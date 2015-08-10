package org.openstreetmap.josm.plugins.scoutsigns.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;


/**
 * Utility class used for handle images and pixel data.
 *
 * @author Beata
 * @version $Revision$
 */
public final class ImageUtil {

    /**
     * Builds an image from the given base64 encoded text.
     *
     * @param data the base64 encoded image data
     * @param width the image's width
     * @param height the image's height
     * @return a {@code BufferedImage} built based on the given data
     * @throws IOException if the image cannot be built
     */
    public static BufferedImage base64ToImage(final String data, final int width, final int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        final byte[] decodedData = DatatypeConverter.parseBase64Binary(data);
        // Base64.decodeBase64(data);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(decodedData)) {
            image = ImageIO.read(bis);
        }
        return image;
    }


    private ImageUtil() {}
}