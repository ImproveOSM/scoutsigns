/*
 *  Copyright 2015 Telenav, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.openstreetmap.josm.plugins.scoutsigns;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Preferences.PreferenceChangeEvent;
import org.openstreetmap.josm.data.Preferences.PreferenceChangedListener;
import org.openstreetmap.josm.gui.IconToggleButton;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.MapView.LayerChangeListener;
import org.openstreetmap.josm.gui.NavigatableComponent;
import org.openstreetmap.josm.gui.NavigatableComponent.ZoomChangeListener;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.scoutsigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.DataSet;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.details.ScoutSignsDetailsDialog;
import org.openstreetmap.josm.plugins.scoutsigns.gui.layer.ScoutSignsLayer;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.observer.TripViewObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.InfoDialog;
import org.openstreetmap.josm.plugins.scoutsigns.util.Util;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.Keys;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import org.openstreetmap.josm.tools.OsmUrlToBounds;


/**
 * Defines the main functionality of the ScoutSigns plugin.
 *
 * @author Bea
 * @version $Revision$
 */
public class ScoutSignsPlugin extends Plugin implements LayerChangeListener, ZoomChangeListener, MouseListener,
PreferenceChangedListener, StatusChangeObserver, TripViewObserver {

    /*
     * Listens to toggle dialog button actions.
     */
    private final class ToggleButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() instanceof IconToggleButton) {
                final IconToggleButton btn = (IconToggleButton) event.getSource();
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        if (btn.isSelected()) {
                            dialog.setVisible(true);
                            btn.setSelected(true);
                        } else {
                            dialog.setVisible(false);
                            btn.setSelected(false);
                            btn.setFocusable(false);
                        }
                        if (layer == null) {
                            registerListeners();
                            addLayer();
                        }
                    }
                });
            }
        }
    }

    /*
     * Downloads the road signs from the current view, and updates the plugin with the new data.
     */
    private final class UpdateThread implements Runnable {

        @Override
        public void run() {
            if (Main.map != null && Main.map.mapView != null) {
                final BoundingBox bbox = Util.buildBBox(Main.map.mapView);
                if (bbox != null) {
                    final int zoom = OsmUrlToBounds.getZoom(Main.map.mapView.getRealBounds());
                    final SearchFilter filter =
                            zoom > ServiceCnf.getInstance().getMaxClusterZoom() ? searchFilter : null;
                            final DataSet result = ServiceHandler.getInstance().searchSigns(bbox, filter, zoom);
                            SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    synchronized (this) {
                                        new InfoDialog().displayDialog(zoom, prevZoom);
                                        prevZoom = zoom;
                                        updateSelection(result);
                                        dialog.enableButtons(zoom);
                                        layer.setDataSet(result);
                                        Main.map.repaint();
                                    }
                                }
                            });
                }
            }
        }

        private void updateSelection(final DataSet result) {
            if (!result.getRoadSignClusters().isEmpty()) {
                dialog.updateData(null);
            } else if (layer.lastSelRoadSign() != null) {
                final RoadSign roadSign =
                        result.getRoadSigns().contains(layer.lastSelRoadSign()) ? layer.lastSelRoadSign() : null;
                        dialog.updateData(roadSign);
            }
        }
    }

    private ScoutSignsLayer layer;
    private ScoutSignsDetailsDialog dialog;
    /** timer for the zoom in/out operations */
    private Timer zoomTimer;

    /** the filters applied to the search */
    private SearchFilter searchFilter;

    private int prevZoom;

    /**
     * Builds a new {@code ScoutSignsPlugin} object. This constructor is automatically invoked by JOSM to bootstrap the
     * plugin.
     *
     * @param info the information about the plugin and its local installation
     */
    public ScoutSignsPlugin(final PluginInformation info) {
        super(info);
        PrefManager.getInstance().saveSupressErrorFlag(false);
        PrefManager.getInstance().saveSuppressMapillaryInfoFlag(false);
        searchFilter = PrefManager.getInstance().loadSearchFilter();
    }


    @Override
    public void activeLayerChange(final Layer layer1, final Layer layer2) {
        // this action is not supported
    }


    @Override
    public void enterTripView() {
        NavigatableComponent.removeZoomChangeListener(this);
        layer.setTripView(true);
        Main.map.repaint();
    }

    @Override
    public void exitTripView() {
        layer.setTripView(false);
        Main.worker.execute(new UpdateThread());
        NavigatableComponent.addZoomChangeListener(this);
    }

    @Override
    public void layerAdded(final Layer newLayer) {
        if (newLayer instanceof ScoutSignsLayer) {
            zoomChanged();
        }
    }

    @Override
    public void layerRemoved(final Layer currentLayer) {
        if (currentLayer instanceof ScoutSignsLayer) {

            // unregister listeners
            NavigatableComponent.removeZoomChangeListener(this);
            MapView.removeLayerChangeListener(this);
            Main.map.mapView.removeMouseListener(this);
            Main.pref.removePreferenceChangeListener(this);
            PrefManager.getInstance().saveSuppressMapillaryInfoFlag(false);

            // remove toggle dialog
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    Main.map.remove(dialog);
                    dialog.getButton().setSelected(false);
                    dialog.setVisible(false);
                    dialog.destroy();
                    layer = null;
                }
            });
        }
    }

    @Override
    public void mapFrameInitialized(final MapFrame oldMapFrame, final MapFrame newMapFrame) {
        if (Main.map != null) {
            dialog = new ScoutSignsDetailsDialog();
            newMapFrame.addToggleDialog(dialog);
            dialog.getButton().addActionListener(new ToggleButtonActionListener());
            registerListeners();
            addLayer();
            prevZoom = OsmUrlToBounds.getZoom(newMapFrame.mapView.getRealBounds());
        }
    }

    @Override
    public void mouseClicked(final MouseEvent event) {
        if (Main.map.mapView.getActiveLayer() == layer && layer.isVisible() && !layer.isTripView()
                && SwingUtilities.isLeftMouseButton(event)) {
            final boolean multiSelect = event.isShiftDown();
            final RoadSign roadSign = layer.nearbyRoadSign(event.getPoint(), multiSelect);

            if (roadSign != null) {
                // a road sign was selected
                if (roadSign.getId() != null) {
                    final Long id = roadSign.getId();
                    Main.worker.execute(new Runnable() {

                        @Override
                        public void run() {
                            retrieveSign(id);
                        }
                    });
                } else {
                    dialog.updateData(roadSign);
                    Main.map.repaint();
                }
            } else if (!multiSelect) {
                // un-select previously selected road sign

                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        dialog.updateData(null);
                        Main.map.repaint();
                    }
                });
            }
        }
    }

    @Override
    public void mouseEntered(final MouseEvent event) {
        // this action is not supported
    }

    @Override
    public void mouseExited(final MouseEvent event) {
        // this action is not supported
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        // this action is not supported
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        // this action is not supported
    }

    @Override
    public void preferenceChanged(final PreferenceChangeEvent event) {
        if (event != null && (event.getNewValue() != null && !event.getNewValue().equals(event.getOldValue()))) {
            if (event.getKey().equals(Keys.FILTERS_CHANGED)) {
                searchFilter = PrefManager.getInstance().loadSearchFilter();
                Main.worker.execute(new UpdateThread());
            }
        }
    }

    @Override
    public void statusChanged(final String ursername, final String text, final Status status, final Long duplicateOf) {
        final List<RoadSign> selRoadSigns = layer.getSelRoadSigns();
        if (!selRoadSigns.isEmpty()) {
            Main.worker.execute(new Runnable() {

                @Override
                public void run() {
                    Long signId;
                    if (selRoadSigns.size() > 1) {
                        ServiceHandler.getInstance().addComments(selRoadSigns, ursername, text, status, duplicateOf);

                        // update details of last road sign
                        signId = selRoadSigns.get(selRoadSigns.size() - 1).getId();
                    } else {
                        signId = selRoadSigns.get(0).getId();
                        ServiceHandler.getInstance().addComment(signId, ursername, text, status, duplicateOf);
                    }
                    retrieveSign(signId);
                }
            });
        }
    }

    @Override
    public void zoomChanged() {
        if (layer != null && layer.isVisible()) {
            if (zoomTimer != null && zoomTimer.isRunning()) {
                zoomTimer.restart();
            } else {
                zoomTimer = new Timer(ServiceCnf.getInstance().getSearchDelay(), new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        Main.worker.execute(new UpdateThread());
                    }
                });
                zoomTimer.setRepeats(false);
                zoomTimer.start();
            }
        }
    }

    private void addLayer() {
        layer = new ScoutSignsLayer();
        Main.main.addLayer(layer);
    }

    private void registerListeners() {
        NavigatableComponent.addZoomChangeListener(this);
        MapView.addLayerChangeListener(this);
        Main.map.mapView.addMouseListener(this);
        Main.pref.addPreferenceChangeListener(this);
        dialog.registerStatusChangeObserver(this);
        dialog.registerTripViewObserver(this);
    }

    private void retrieveSign(final Long signId) {
        final RoadSign roadSign = ServiceHandler.getInstance().retrieveSign(signId);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                dialog.updateData(roadSign);
                layer.updateSelRoadSign(roadSign);
                Main.map.repaint();
            }
        });
    }
}