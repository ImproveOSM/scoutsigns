/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;


/**
 * Utility class used for handle images and pixel data.
 *
 * @author Beata
 * @version $Revision$
 */
public final class ImageUtil {

    private ImageUtil() {}

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
        final byte[] decodedData = Base64.getMimeDecoder().decode(data);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(decodedData)) {
            image = ImageIO.read(bis);
        }
        return image;
    }
}