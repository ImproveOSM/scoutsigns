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

import static org.openstreetmap.josm.plugins.scoutsigns.argument.MapillaryImageSize.PX_1024;
import static org.openstreetmap.josm.plugins.scoutsigns.argument.MapillaryImageSize.PX_320;
import static org.openstreetmap.josm.plugins.scoutsigns.argument.MapillaryImageSize.PX_640;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.scoutsigns.argument.MapillaryImageSize;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Source;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.util.ImageUtil;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.tools.OpenBrowser;


/**
 * Defines a frame for displaying the photo of a selected road sign.
 *
 * @author Beata
 * @version $Revision$
 */
class ImageFrame extends JFrame {

    /*
     * If the user clicks on a Mapillary image, the image home page is opened in a browser.
     */
    private final class ImageMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(final MouseEvent event) {
            if ((event.getSource() instanceof JLabel) && roadSign != null && roadSign.getKey() != null) {
                final String link = ServiceCnf.getInstance().getMapillaryImagePage() + roadSign.getKey();
                OpenBrowser.displayUrl(link);
            }
        }
    }

    /*
     * Reloads the image of a Mapillary road sign.
     */
    private final class ReloadImage extends AbstractAction {

        private static final long serialVersionUID = -5814556940033382729L;
        private final MapillaryImageSize size;

        private ReloadImage(final MapillaryImageSize size) {
            this.size = size;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            updateComponents(size);
            pack();
        }
    }

    private static final long serialVersionUID = -4511721458975966411L;

    private static final String THUMB = "/thumb-";
    private static final String EXT = ".jpg";
    private static final Dimension DIM = new Dimension(250, 200);
    private static final int WIDTH = 12;
    private static final int HEIGHT = 75;

    private final JLabel lblImage;
    private final JPanel pnlBtn;
    private final JPanel pnlImage;

    private RoadSign roadSign = null;


    ImageFrame(final RoadSign roadSign) {
        this();
        update(roadSign);
    }

    private ImageFrame() {
        setTitle(GuiCnf.getInstance().getFrmPhotoTitle());
        setIconImage(IconCnf.getInstance().getPhotoIcon().getImage());
        setResizable(true);
        setLocationRelativeTo(Main.map);
        setAlwaysOnTop(true);

        // create components
        pnlImage = new JPanel(new BorderLayout());
        lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        lblImage.setVerticalAlignment(JLabel.CENTER);
        lblImage.addMouseListener(new ImageMouseListener());
        pnlBtn = new JPanel(new FlowLayout());
        pnlBtn.add(Builder.buildButton(new ReloadImage(PX_320), PX_320.toString()));
        pnlBtn.add(Builder.buildButton(new ReloadImage(PX_640), PX_640.toString()));
        pnlBtn.add(Builder.buildButton(new ReloadImage(PX_1024), PX_1024.toString()));
        add(pnlImage);
    }


    void update(final RoadSign roadSign) {
        this.roadSign = roadSign;
        if (roadSign.getSource() == Source.SCOUT) {
            updateComponents();
        } else {
            updateComponents(PX_640);
        }
        pack();
    }

    private void updateComponents() {
        if (roadSign.getImage() == null) {
            lblImage.setText(GuiCnf.getInstance().getLblPhotoMissing());
        } else {
            try {
                final BufferedImage bi =
                        ImageUtil.base64ToImage(roadSign.getImage().getData(), roadSign.getImage().getWidth(), roadSign
                                .getImage().getHeight());
                lblImage.setIcon(new ImageIcon(bi));
            } catch (final IOException e) {
                lblImage.setText(GuiCnf.getInstance().getLblPhotoError());
            }
        }
        setPreferredSize(DIM);
        pnlImage.removeAll();
        pnlImage.add(lblImage, BorderLayout.CENTER);
    }

    private void updateComponents(final MapillaryImageSize imageSize) {
        BufferedImage image = null;
        final StringBuilder link = new StringBuilder(ServiceCnf.getInstance().getMapillaryImageUrl());
        link.append(roadSign.getKey()).append(THUMB).append(imageSize.getValue()).append(EXT);
        try {
            image = ImageIO.read(new URL(link.toString()));
        } catch (final IOException ex) {
            lblImage.setText(GuiCnf.getInstance().getLblPhotoError());
            setPreferredSize(DIM);
        }
        if (image == null) {
            lblImage.setText(GuiCnf.getInstance().getLblPhotoMissing());
            setPreferredSize(DIM);
        } else {
            lblImage.setIcon(new ImageIcon(image));
            setPreferredSize(new Dimension(image.getWidth() + WIDTH, image.getHeight() + HEIGHT));
        }
        pnlImage.removeAll();
        pnlImage.add(lblImage, BorderLayout.CENTER);
        pnlImage.add(pnlBtn, BorderLayout.SOUTH);
    }
}