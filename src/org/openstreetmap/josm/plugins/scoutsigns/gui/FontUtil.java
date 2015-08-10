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