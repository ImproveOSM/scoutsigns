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
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.util.ImageUtil;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;


/**
 * Defines a frame for displaying the photo of a selected road sign.
 *
 * @author Beata
 * @version $Revision$
 */
class ImageFrame extends JFrame {

    private static final long serialVersionUID = -4511721458975966411L;

    private static final Dimension DIM = new Dimension(250, 200);

    private final JLabel lblImage;
    private final JPanel pnlImage;

    private RoadSign roadSign = null;


    private ImageFrame() {
        setTitle(GuiConfig.getInstance().getFrmPhotoTitle());
        setIconImage(IconConfig.getInstance().getPhotoIcon().getImage());
        setResizable(true);
        setLocationRelativeTo(Main.map);
        setAlwaysOnTop(true);

        // create components
        pnlImage = new JPanel(new BorderLayout());
        lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        lblImage.setVerticalAlignment(JLabel.CENTER);
        add(pnlImage);
    }

    ImageFrame(final RoadSign roadSign) {
        this();
        update(roadSign);
    }


    private void updateComponents() {
        if (roadSign.getImage() == null) {
            lblImage.setText(GuiConfig.getInstance().getLblPhotoMissing());
        } else {
            try {
                final BufferedImage bi = ImageUtil.base64ToImage(roadSign.getImage().getData(),
                        roadSign.getImage().getWidth(), roadSign.getImage().getHeight());
                lblImage.setIcon(new ImageIcon(bi));
            } catch (final IOException e) {
                lblImage.setText(GuiConfig.getInstance().getLblPhotoError());
            }
        }
        setPreferredSize(DIM);
        pnlImage.removeAll();
        pnlImage.add(lblImage, BorderLayout.CENTER);
    }


    void update(final RoadSign roadSign) {
        this.roadSign = roadSign;
        updateComponents();
        pack();
    }
}