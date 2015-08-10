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

    ImageFrame(final RoadSign roadSign) {
        this();
        update(roadSign);
    }


    private void updateComponents() {
        if (roadSign.getImage() == null) {
            lblImage.setText(GuiCnf.getInstance().getLblPhotoMissing());
        } else {
            try {
                final BufferedImage bi = ImageUtil.base64ToImage(roadSign.getImage().getData(),
                        roadSign.getImage().getWidth(), roadSign.getImage().getHeight());
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

    void update(final RoadSign roadSign) {
        this.roadSign = roadSign;
        if (roadSign.getSource() == Source.SCOUT) {
            updateComponents();
        } else {
            updateComponents(PX_640);
        }
        pack();
    }
}