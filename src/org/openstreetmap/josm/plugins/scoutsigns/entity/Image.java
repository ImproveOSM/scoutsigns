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
package org.openstreetmap.josm.plugins.scoutsigns.entity;


/**
 * Defines the image business entity.
 *
 * @author Beata
 * @version $Revision$
 */
public class Image {

    private final int width;
    private final int height;
    private final String data;


    /**
     * Builds a new object with the given arguments.
     *
     * @param width the width of the image in pixels
     * @param height the height of the image in pixels
     * @param data the binary data of the image, encoded as a BASE64 text
     */
    public Image(final int width, final int height, final String data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }


    public String getData() {
        return data;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}