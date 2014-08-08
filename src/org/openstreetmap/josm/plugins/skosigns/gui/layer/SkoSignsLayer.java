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
package org.openstreetmap.josm.plugins.skosigns.gui.layer;

import java.util.Collection;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.Action;
import javax.swing.Icon;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.dialogs.LayerListDialog;
import org.openstreetmap.josm.gui.dialogs.LayerListPopup;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.skosigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.skosigns.util.Util;
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
    
    private PaintHandler paintHandler;
    private Collection<RoadSign> roadSigns;
    private RoadSign selRoadSign;
    
    
    /**
     * Builds a new {@code SkoSignsLayer} with default functionality.
     */
    public SkoSignsLayer() {
        super(GuiCnf.getInstance().getDlgTitle());
        paintHandler = new PaintHandler();
    }
    
    /**
     * Returns the road sign near to the given point. The method returns null if
     * there is no nearby road sign.
     * 
     * @param point a {@code Point}
     * @return a {@code RoadSign}
     */
    public RoadSign nearbyRoadSign(Point point) {
        RoadSign roadSign = Util.nearbyRoadSign(roadSigns, point);
        setSelRoadSign(roadSign);
        return selRoadSign;
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
    public void paint(Graphics2D g2D, MapView mv, Bounds bounds) {
        mv.setDoubleBuffered(true);
        if (roadSigns != null) {
            paintHandler.drawRoadSigns(g2D, mv, roadSigns, selRoadSign);
        }
    }
    
    @Override
    public void visitBoundingBox(BoundingXYVisitor arg0) {
        // not supported
    }
    
    private void setSelRoadSign(RoadSign selRoadSign) {
        this.selRoadSign = selRoadSign;
    }
    
    public void setRoadSigns(Collection<RoadSign> roadSigns) {
        this.roadSigns = roadSigns;
    }
}