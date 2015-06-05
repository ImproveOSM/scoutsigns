/*
 * Copyright (c) 2014, skobbler GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Jun 17, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
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
import org.openstreetmap.josm.plugins.scoutsigns.util.Util;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
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
                    final DataSet result = ServiceHandler.getInstance().searchSigns(bbox, searchFilter, zoom);
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            synchronized (this) {
                                displayClusterInfoDialog(zoom);
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

        private void displayClusterInfoDialog(final int zoom) {
            if (Util.shouldDisplayClInfoDialog(zoom, prevZoom)) {
                final int val =
                        JOptionPane.showOptionDialog(Main.map.mapView, GuiCnf.getInstance().getInfoClusterTxt(), GuiCnf
                                .getInstance().getInfoClusterTitle(), JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null, null, null);
                final boolean flag = val == JOptionPane.YES_OPTION;
                PrefManager.getInstance().saveSuppressClusterInfoFlag(flag);
            }
        }

        private void updateSelection(final DataSet result) {
            if (!result.getRoadSignClusters().isEmpty()) {
                dialog.updateData(null);
            } else if (layer.lastSelRoadSign() != null) {
                dialog.updateData(layer.lastSelRoadSign());
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


    /* ZoomChangeListener method */

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


    /* LayerChangeListener methods */

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


    /* MouseListener methods */

    @Override
    public void layerAdded(final Layer newLayer) {
        if (newLayer instanceof ScoutSignsLayer) {
            Main.map.mapView.moveLayer(newLayer, 0);
            Main.map.mapView.setActiveLayer(newLayer);
            zoomChanged();
        }
    }

    @Override
    public void layerRemoved(final Layer currentLayer) {
        if (currentLayer instanceof ScoutSignsLayer) {
            // remove the layer & toggle dialog
            Main.map.mapView.removeLayer(layer);
            Main.map.remove(dialog);
            dialog.getButton().setSelected(false);
            dialog.setVisible(false);
            dialog.destroy();
            layer = null;

            // unregister listeners
            removeListeners();
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

                final Long id = roadSign.getId();
                Main.worker.execute(new Runnable() {

                    @Override
                    public void run() {
                        retrieveSign(id);
                    }
                });
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


    /* PreferenceChangedListener method */

    @Override
    public void mouseExited(final MouseEvent event) {
        // this action is not supported
    }


    /* StatusChangeObserver method */

    @Override
    public void mousePressed(final MouseEvent event) {
        // this action is not supported
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        // this action is not supported
    }

    /* TripViewObserver implementation */

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
            if (selRoadSigns.size() > 1) {
                // add comment/ change status of multiple road signs
                Main.worker.execute(new Runnable() {

                    @Override
                    public void run() {
                        ServiceHandler.getInstance().addComments(selRoadSigns, ursername, text, status, duplicateOf);

                        // update details of last road sign
                        final Long lastId = selRoadSigns.get(selRoadSigns.size() - 1).getId();
                        retrieveSign(lastId);
                    }
                });
            } else {
                // add comment/change status of a single road sign
                final Long signId = selRoadSigns.get(0).getId();
                Main.worker.execute(new Runnable() {

                    @Override
                    public void run() {
                        ServiceHandler.getInstance().addComment(signId, ursername, text, status, duplicateOf);

                        // update details of the selected road sign
                        retrieveSign(signId);
                    }
                });
            }
        }
    }


    /* local methods & classes */

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

    private void removeListeners() {
        NavigatableComponent.removeZoomChangeListener(this);
        MapView.removeLayerChangeListener(this);
        Main.map.mapView.removeMouseListener(this);
        Main.pref.removePreferenceChangeListener(this);
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