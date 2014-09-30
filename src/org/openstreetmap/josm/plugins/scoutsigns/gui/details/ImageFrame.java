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
 * Created on Sep 15, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Image;
import org.openstreetmap.josm.plugins.scoutsigns.util.ImageUtil;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;


/**
 * Defines a frame for displaying the photo of a selected road sign.
 * 
 * @author Beata
 * @version $Revision$
 */
class ImageFrame extends JFrame {
    
    private static final long serialVersionUID = -4511721458975966411L;
    
    private static final Dimension DIM = new Dimension(250, 200);
    
    
    /**
     * Builds a new frame for the given image.
     * 
     * @param image a {@code Image}
     */
    ImageFrame(Image image) {
        setTitle(GuiCnf.getInstance().getFrmPhotoTitle());
        setIconImage(IconCnf.getInstance().getPhotoIcon().getImage());
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(Main.map.mapView);
        setAlwaysOnTop(true);
        setPreferredSize(DIM);
        addComponent(image);
        repaint();
    }
    
    
    private void addComponent(Image image) {
        JLabel lbl;
        if (image == null) {
            lbl = new JLabel(GuiCnf.getInstance().getLblPhotoMissing());
        } else {
            try { BufferedImage bi = ImageUtil.base64ToImage(image.getData(),
                    image.getWidth(), image.getHeight());
                lbl = new JLabel(new ImageIcon(bi));
            } catch (IOException ex) {
                lbl = new JLabel(GuiCnf.getInstance().getLblPhotoError());
            }
        }
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.add(lbl, BorderLayout.CENTER);
        add(pnl);
    }
}