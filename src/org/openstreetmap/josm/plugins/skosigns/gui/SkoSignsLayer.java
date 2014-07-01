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
package org.openstreetmap.josm.plugins.skosigns.gui;

import java.awt.Graphics2D;
import javax.swing.Action;
import javax.swing.Icon;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.dialogs.LayerListDialog;
import org.openstreetmap.josm.gui.dialogs.LayerListPopup;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.TltCnf;


/**
 * Defines the skosigns layer main functionality.
 * 
 * @author Bea
 * @version $Revision$
 */
public class SkoSignsLayer extends Layer {
    
    
    /**
     * Builds a new {@code SkoSignsLayer} with default functionality.
     */
    public SkoSignsLayer() {
        super(GuiCnf.getInstance().getDlgTitle());
    }
    
    @Override
    public Icon getIcon() {
        return IconCnf.getInstance().getLayerIcon();
    }
    
    @Override
    public Object getInfoComponent() {
        return TltCnf.getInstance().getLayerInfo();
    }
    
    @Override
    public Action[] getMenuEntries() {
        LayerListDialog layerListDlg = LayerListDialog.getInstance();
        return new Action[] { layerListDlg.createActivateLayerAction(this),
                layerListDlg.createShowHideLayerAction(),
                layerListDlg.createDeleteLayerAction(),
                SeparatorLayerAction.INSTANCE,
                new LayerListPopup.InfoAction(this) };
    }
    
    
    @Override
    public String getToolTipText() {
        return TltCnf.getInstance().getPluginTlt();
    }
    
    @Override
    public boolean isMergable(Layer layer) {
        return false;
    }
    
    @Override
    public void mergeFrom(Layer layer) {
        // merge operation is not supported
    }
    
    @Override
    public void paint(Graphics2D arg0, MapView arg1, Bounds arg2) {
        // TODO add road sign drawing logic
    }
    
    @Override
    public void visitBoundingBox(BoundingXYVisitor arg0) {
        // TODO Auto-generated method stub
    }
}