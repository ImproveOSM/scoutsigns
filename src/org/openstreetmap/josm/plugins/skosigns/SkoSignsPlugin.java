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

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.MapView.LayerChangeListener;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.skosigns.gui.SkoSignsDetailsDialog;
import org.openstreetmap.josm.plugins.skosigns.gui.SkoSignsLayer;


/**
 * Defines the main functionality of the skosigns plugin.
 * 
 * @author Bea
 * @version $Revision$
 */
public class SkoSignsPlugin extends Plugin implements LayerChangeListener {
    
    private SkoSignsLayer layer;
    private SkoSignsDetailsDialog dialog;
    
    
    /**
     * Builds a new {@code SkoSignsPlugin} object. This constructor is
     * automatically invoked by JOSM to bootstrap the plugin.
     * 
     * @param info the information about the plugin and its local installation
     */
    public SkoSignsPlugin(PluginInformation info) {
        super(info);
    }
    
    
    @Override
    public void mapFrameInitialized(MapFrame oldMapFrame, MapFrame newMapFrame) {
        if (Main.map != null) {
            dialog = new SkoSignsDetailsDialog();
            newMapFrame.addToggleDialog(dialog);
            MapView.addLayerChangeListener(this);
        }
    }
    
    
    @Override
    public void activeLayerChange(Layer layer1, Layer layer2) {
        // this operation is not supported
    }
    
    @Override
    public void layerAdded(Layer newLayer) {
        if (newLayer != null && layer == null) {
            layer = new SkoSignsLayer();
            Main.main.addLayer(layer);
            Main.map.mapView.moveLayer(layer, 0);
        }
    }
    
    @Override
    public void layerRemoved(Layer currentLayer) {
        if (currentLayer instanceof SkoSignsLayer) {
            Main.map.mapView.removeLayer(layer);
            Main.map.remove(dialog);
        }
    }
}