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
 * Created on Jul 30, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.skosigns.gui.layer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Collection;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.skosigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.skosigns.gui.TypeIconFactory;


/**
 * Handles the drawing operations of the layer data.
 * 
 * @author Beata
 * @version $Revision$
 */
class PaintHandler {
    
    private TypeIconFactory iconFactory = TypeIconFactory.getInstance();
    
    
    /**
     * Draws the given collection of {@code RoadSign}s to the map.
     * 
     * @param g2D the {@code Graphics2D} used to performed the drawing
     * @param mv the current {@code MapView}
     * @param roadSigns the {@code RoadSign} collection to be displayed
     */
    void drawRoadSigns(Graphics2D g2D, MapView mv,
            Collection<RoadSign> roadSigns, RoadSign selRoadSign) {
        for (RoadSign roadSign : roadSigns) {
            Point point = mv.getPoint(roadSign.getSignPos().getPosition());
            if (mv.contains(point)) {
                ImageIcon icon = null;
                boolean selected = roadSign.equals(selRoadSign);
                icon = iconFactory.getIcon(roadSign.getType(), selected);
                drawIcon(g2D, icon, point);
            }
        }
    }
    
    private void drawIcon(Graphics2D g2D, ImageIcon icon, Point p) {
        g2D.drawImage(icon.getImage(), p.x - (icon.getIconWidth() / 2), p.y
                - (icon.getIconHeight() / 2), new ImageObserver() {
            
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y,
                    int width, int height) {
                return false;
            }
        });
    }
}