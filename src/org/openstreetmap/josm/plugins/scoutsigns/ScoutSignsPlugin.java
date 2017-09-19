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

import java.awt.GraphicsEnvironment;
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
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.NavigatableComponent;
import org.openstreetmap.josm.gui.NavigatableComponent.ZoomChangeListener;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerAddEvent;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerChangeListener;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerOrderChangeEvent;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerRemoveEvent;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.DataSet;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.details.ScoutSignsDetailsDialog;
import org.openstreetmap.josm.plugins.scoutsigns.gui.layer.ScoutSignsLayer;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.observer.TripViewObserver;
import org.openstreetmap.josm.plugins.scoutsigns.service.ServiceHandler;
import org.openstreetmap.josm.plugins.scoutsigns.util.InfoDialog;
import org.openstreetmap.josm.plugins.scoutsigns.util.Util;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.Keys;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import org.openstreetmap.josm.tools.Logging;
import org.openstreetmap.josm.tools.OsmUrlToBounds;
import com.telenav.josm.common.argument.BoundingBox;
import com.telenav.josm.common.thread.ThreadPool;


/**
 * Defines the main functionality of the ScoutSigns plugin.
 *
 * @author Bea
 * @version $Revision$
 */
public class ScoutSignsPlugin extends Plugin implements LayerChangeListener, ZoomChangeListener, MouseListener,
PreferenceChangedListener, StatusChangeObserver, TripViewObserver {


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
        searchFilter = PrefManager.getInstance().loadSearchFilter();
    }

    @Override
    public void mapFrameInitialized(final MapFrame oldMapFrame, final MapFrame newMapFrame) {
        if (MainApplication.getMap() != null && !GraphicsEnvironment.isHeadless()) {
            dialog = new ScoutSignsDetailsDialog();
            newMapFrame.addToggleDialog(dialog);
            dialog.getButton().addActionListener(new ToggleButtonActionListener());
            registerListeners();
            layer = new ScoutSignsLayer();
            newMapFrame.mapView.getLayerManager().addLayer(layer);
            if (!dialog.getButton().isSelected()) {
                dialog.getButton().doClick();
            }
            prevZoom = OsmUrlToBounds.getZoom(newMapFrame.mapView.getRealBounds());
        }
        if (oldMapFrame != null && newMapFrame == null) {
            // clean-up
            try {
                ThreadPool.getInstance().shutdown();
            } catch (final InterruptedException e) {
                Logging.error("Could not shutdown thead pool.", e);
            }
        }
    }


    /* LayerChangeListener methods */

    @Override
    public void layerAdded(final LayerAddEvent event) {
        if (event.getAddedLayer() instanceof ScoutSignsLayer) {
            zoomChanged();
        }
    }

    @Override
    public void layerOrderChanged(final LayerOrderChangeEvent event) {
        // no logic for this method
    }

    @Override
    public void layerRemoving(final LayerRemoveEvent event) {
        if (event.getRemovedLayer() instanceof ScoutSignsLayer) {
            // unregister listeners
            NavigatableComponent.removeZoomChangeListener(this);
            MainApplication.getLayerManager().removeLayerChangeListener(this);
            Main.pref.removePreferenceChangeListener(this);

            if (MainApplication.getMap() != null) {
                MainApplication.getMap().mapView.removeMouseListener(this);
                MainApplication.getMap().remove(dialog);
                layer = null;
                dialog.hideDialog();
            }
        }
    }


    /* ZoomChangeListener method */

    @Override
    public void zoomChanged() {
        if (layer != null && layer.isVisible()) {
            if (zoomTimer != null && zoomTimer.isRunning()) {
                zoomTimer.restart();
            } else {
                zoomTimer = new Timer(Config.getInstance().getSearchDelay(),
                        event -> ThreadPool.getInstance().execute(new UpdateThread()));
                zoomTimer.setRepeats(false);
                zoomTimer.start();
            }
        }
    }

    /* MouseListener methods */

    @Override
    public void mouseClicked(final MouseEvent event) {
        if (MainApplication.getLayerManager().getActiveLayer() == layer && layer.isVisible() && !layer.isTripView()
                && SwingUtilities.isLeftMouseButton(event)) {
            final boolean multiSelect = event.isShiftDown();
            final RoadSign roadSign = layer.nearbyRoadSign(event.getPoint(), multiSelect);

            if (roadSign != null) {
                // a road sign was selected
                if (roadSign.getId() != null) {
                    final Long id = roadSign.getId();
                    ThreadPool.getInstance().execute(() -> {
                        retrieveSign(id);
                    });
                } else {
                    dialog.updateData(roadSign);
                    layer.invalidate();
                    MainApplication.getMap().mapView.repaint();
                }
            } else if (!multiSelect) {
                // un-select previously selected road sign

                SwingUtilities.invokeLater(() -> {
                    dialog.updateData(null);
                    layer.invalidate();
                    MainApplication.getMap().mapView.repaint();
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


    /* PreferenceChangedListener methods */
    @Override
    public void preferenceChanged(final PreferenceChangeEvent event) {
        if (event != null && (event.getNewValue() != null && !event.getNewValue().equals(event.getOldValue()))) {
            if (event.getKey().equals(Keys.FILTERS_CHANGED)) {
                searchFilter = PrefManager.getInstance().loadSearchFilter();
                ThreadPool.getInstance().execute(new UpdateThread());
            }
        }
    }


    /* StatusChangeObserver method */
    @Override
    public void statusChanged(final String ursername, final String text, final Status status, final Long duplicateOf) {
        final List<RoadSign> selRoadSigns = layer.getSelRoadSigns();
        if (!selRoadSigns.isEmpty()) {
            ThreadPool.getInstance().execute(() -> {
                Long signId;
                if (selRoadSigns.size() > 1) {
                    ServiceHandler.getInstance().addComments(selRoadSigns, ursername, text, status, duplicateOf);

                    // update details of last road sign
                    signId = selRoadSigns.get(selRoadSigns.size() - 1).getId();
                } else {
                    signId = selRoadSigns.get(0).getId();
                    ServiceHandler.getInstance().addComment(signId, ursername, text, status, duplicateOf);
                }
                for (final RoadSign roadSign : selRoadSigns) {
                    if (!roadSign.getStatus().equals(status)) {
                        if (layer.isTripView()) {
                            exitTripView();
                        } else {
                            ThreadPool.getInstance().execute(new UpdateThread());
                        }
                        break;
                    }
                }
            });
        }
    }


    /* TripViewObserver methods */

    @Override
    public void enterTripView() {
        NavigatableComponent.removeZoomChangeListener(this);
        layer.setTripView(true);
        layer.invalidate();
        MainApplication.getMap().mapView.repaint();
    }

    @Override
    public void exitTripView() {
        layer.setTripView(false);
        ThreadPool.getInstance().execute(new UpdateThread());
        NavigatableComponent.addZoomChangeListener(this);
    }


    private void registerListeners() {
        NavigatableComponent.addZoomChangeListener(this);
        MainApplication.getLayerManager().addLayerChangeListener(this);
        MainApplication.getMap().mapView.addMouseListener(this);
        Main.pref.addPreferenceChangeListener(this);
        dialog.registerStatusChangeObserver(this);
        dialog.registerTripViewObserver(this);
    }

    private void retrieveSign(final Long signId) {
        final RoadSign roadSign = ServiceHandler.getInstance().retrieveSign(signId);
        SwingUtilities.invokeLater(() -> {
            dialog.updateData(roadSign);
            layer.updateSelRoadSign(roadSign);
            layer.invalidate();
            MainApplication.getMap().mapView.repaint();
        });
    }


    /*
     * Listens to toggle dialog button actions.
     */
    private class ToggleButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() instanceof IconToggleButton) {
                final IconToggleButton btn = (IconToggleButton) event.getSource();
                SwingUtilities.invokeLater(() -> {
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
                        layer = new ScoutSignsLayer();
                        MainApplication.getMap().mapView.getLayerManager().addLayer(layer);
                    }
                });
            }
        }
    }


    /*
     * Downloads the road signs from the current view, and updates the plugin with the new data.
     */
    private class UpdateThread implements Runnable {

        @Override
        public void run() {
            if (MainApplication.getMap() != null && MainApplication.getMap().mapView != null) {
                final BoundingBox bbox = Util.buildBBox(MainApplication.getMap().mapView);
                if (bbox != null) {
                    final int zoom = OsmUrlToBounds.getZoom(MainApplication.getMap().mapView.getRealBounds());
                    final SearchFilter filter = zoom > Config.getInstance().getMaxClusterZoom() ? searchFilter : null;
                    final DataSet result = ServiceHandler.getInstance().search(bbox, filter, zoom);
                    SwingUtilities.invokeLater(() -> {
                        new InfoDialog().displayDialog(zoom, prevZoom);
                        prevZoom = zoom;
                        updateSelection(result);
                        dialog.enableButtons(zoom, layer.isTripView());
                        layer.setDataSet(result);
                        layer.invalidate();
                        MainApplication.getMap().mapView.repaint();
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
}