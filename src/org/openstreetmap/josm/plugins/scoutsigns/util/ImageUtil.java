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
package org.openstreetmap.josm.plugins.scoutsigns.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Base64;


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
        final byte[] decodedData = Base64.getDecoder().decode(data);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(decodedData)) {
            image = ImageIO.read(bis);
        }
        return image;
    }
    
}