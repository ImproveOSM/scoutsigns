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
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import org.openstreetmap.josm.Main;


/**
 * Defines the font types used by the UI.
 *
 * @author Bea
 * @version $Revision$
 */
public final class FontUtil {

    public static final Font PLAIN_12 = new Font("Times New Roman", Font.PLAIN, 12);
    /* bold fonts */
    public static final Font BOLD_12 = new Font("Times New Roman", Font.BOLD, 12);

    /* font metrics */
    public static final FontMetrics FM_PLAIN_12 = Main.map.mapView.getGraphics().getFontMetrics(PLAIN_12);

    public static final FontMetrics FM_BOLD_12 = Main.map.mapView.getGraphics().getFontMetrics(BOLD_12);

    private FontUtil() {}
}