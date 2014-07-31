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
package org.openstreetmap.josm.plugins.skosigns;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.NavigatableComponent;
import org.openstreetmap.josm.gui.MapView.LayerChangeListener;
import org.openstreetmap.josm.gui.NavigatableComponent.ZoomChangeListener;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.skosigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.skosigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.skosigns.gui.details.SkoSignsDetailsDialog;
import org.openstreetmap.josm.plugins.skosigns.gui.layer.SkoSignsLayer;
import org.openstreetmap.josm.plugins.skosigns.util.MapUtil;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.plugins.skosigns.util.pref.PrefManager;
import org.openstreetmap.josm.tools.OsmUrlToBounds;


/**
 * Defines the main functionality of the skosigns plugin.
 * 
 * @author Bea
 * @version $Revision$
 */
public class SkoSignsPlugin extends Plugin implements LayerChangeListener,
        ZoomChangeListener {
    
    private SkoSignsLayer layer;
    private SkoSignsDetailsDialog dialog;
    
    
    /** timer for the zoom in/out operations */
    private Timer zoomTimer;
    
    
    /**
     * Builds a new {@code SkoSignsPlugin} object. This constructor is
     * automatically invoked by JOSM to bootstrap the plugin.
     * 
     * @param info the information about the plugin and its local installation
     */
    public SkoSignsPlugin(PluginInformation info) {
        super(info);
        PrefManager.getInstance().saveSupressErrorFlag(false);
    }
    
    
    @Override
    public void mapFrameInitialized(MapFrame oldMapFrame, MapFrame newMapFrame) {
        if (Main.map != null) {
            dialog = new SkoSignsDetailsDialog();
            newMapFrame.addToggleDialog(dialog);
            NavigatableComponent.addZoomChangeListener(this);
            MapView.addLayerChangeListener(this);
        }
    }
    
    @Override
    public void activeLayerChange(Layer layer1, Layer layer2) {
        // no action
    }
    
    @Override
    public void layerAdded(Layer newLayer) {
        if (newLayer != null && layer == null) {
            Main.worker.execute(new Runnable() {
                @Override
                public void run() {
                    layer = new SkoSignsLayer();
                    Main.main.addLayer(layer);
                    Main.map.mapView.setActiveLayer(layer);
                    Main.map.mapView.moveLayer(layer, 0);
                    zoomChanged();
                }
            });
        }
    }
    
    @Override
    public void layerRemoved(Layer currentLayer) {
        if (currentLayer instanceof SkoSignsLayer) {
            Main.map.mapView.removeLayer(layer);
            Main.map.remove(dialog);
        }
    }
    
    @Override
    public void zoomChanged() {
        if (dialog.isShowing()) {
            if (zoomTimer != null && zoomTimer.isRunning()) {
                zoomTimer.restart();
            } else {
                zoomTimer = new Timer(ServiceCnf.getInstance().getSearchDelay(),
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Main.worker.execute(new UpdateThread());
                                }
                            });
                zoomTimer.setRepeats(false);
                zoomTimer.start();
            }
        }
    }
    
    private void updateView(final Collection<RoadSign> roadSigns) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                layer.setRoadSigns(roadSigns);
                Main.map.repaint();
            }
        });
    }
    
    /**
     * Downloads the road signs from the current view, and updates the plugin 
     * with the new data.
     * 
     * @author Beata
     * @version $Revision$
     */
    private final class UpdateThread implements Runnable {
        
        @Override
        public void run() {
            if (Main.map != null && Main.map.mapView != null) {
                BoundingBox bbox = MapUtil.buildBBox(Main.map.mapView);
                if (bbox != null) {
                    int zoom = OsmUrlToBounds.getZoom(Main.map.mapView.
                            getRealBounds());
                    Collection<RoadSign> roadSigns =
                            ServiceHandler.getInstance().searchSigns(bbox, 
                                    null, zoom);
                    updateView(roadSigns);
                }
            }
        }
    }
}