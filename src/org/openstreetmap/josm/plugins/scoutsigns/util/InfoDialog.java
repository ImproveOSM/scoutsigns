package org.openstreetmap.josm.plugins.scoutsigns.util;

import javax.swing.JOptionPane;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


/**
 * Helper class, displays a
 */
public final class InfoDialog {

    /**
     * Displays a dialog window with the corresponding message for the following situations:
     * <ul>
     * <li>user enters cluster view from previous road sign view - info regarding cluster view is displayed</li>
     * <li>user is in cluster view - info regarding Mapillary road signs is displayeda</li>
     * </ul>
     *
     * @param zoom the current zoom level
     * @param prevZoom the previous zom level
     */
    public void displayDialog(final int zoom, final int prevZoom) {
        final int maxZoom = ServiceCnf.getInstance().getMaxClusterZoom();
        if (!PrefManager.getInstance().loadSuppressClusterInfoFlag() && (zoom <= maxZoom && zoom < prevZoom)) {
            final int val = JOptionPane.showOptionDialog(Main.map.mapView, GuiCnf.getInstance().getInfoClusterTxt(),
                    GuiCnf.getInstance().getInfoClusterTitle(), JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);
            final boolean flag = val == JOptionPane.YES_OPTION;
            PrefManager.getInstance().saveSuppressClusterInfoFlag(flag);
        } else if (!PrefManager.getInstance().loadSupressMapillaryInfoFlag() && zoom < maxZoom) {
            final int val = JOptionPane.showOptionDialog(Main.map.mapView, GuiCnf.getInstance().getInfoMapillaryTxt(),
                    GuiCnf.getInstance().getInfoMapillaryTitle(), JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);
            final boolean flag = val == JOptionPane.YES_OPTION;
            PrefManager.getInstance().saveSuppressMapillaryInfoFlag(flag);
        }
    }
}
