/*
 * Copyright (c) 2013, skobbler GmbH
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
 * Created on Nov 4, 2013 by Bea
 * Modified on $DateTime$ by $Author$
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

    /* plain fonts */
    public static final Font PLAIN_11 = new Font("Times New Roman", Font.PLAIN, 11);

    public static final Font PLAIN_12 = new Font("Times New Roman", Font.PLAIN, 12);
    /* bold fonts */
    public static final Font BOLD_12 = new Font("Times New Roman", Font.BOLD, 12);

    public static final Font BOLD_13 = new Font("Times New Roman", Font.BOLD, 13);
    public static final Font BOLD_14 = new Font("Times New Roman", Font.BOLD, 14);
    /* font metrics */
    public static final FontMetrics FM_PLAIN_12 = Main.map.mapView.getGraphics().getFontMetrics(PLAIN_12);

    public static final FontMetrics FM_BOLD_12 = Main.map.mapView.getGraphics().getFontMetrics(BOLD_12);

    private FontUtil() {}
}