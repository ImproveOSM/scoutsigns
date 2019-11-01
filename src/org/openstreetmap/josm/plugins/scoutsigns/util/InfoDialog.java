/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.util;

import javax.swing.JOptionPane;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


/**
 * Helper class, displays an info dialog window.
 */
public final class InfoDialog {

    private static boolean isDisplayed = false;

    /**
     * Displays a dialog window with the corresponding message for the following situations:
     * <ul>
     * <li>user enters cluster view from previous road sign view - info regarding cluster view is displayed</li>
     * <li>user is in cluster view - info regarding Mapillary road signs is displayed</li>
     * </ul>
     *
     * @param zoom the current zoom level
     * @param prevZoom the previous zoom level
     */
    public synchronized void displayDialog(final int zoom, final int prevZoom) {
        if (!isDisplayed) {
            final int maxZoom = Config.getInstance().getMaxClusterZoom();
            if (!PrefManager.getInstance().loadSuppressClusterInfoFlag() && (zoom <= maxZoom && zoom < prevZoom)) {
                isDisplayed = true;
                final int val = JOptionPane.showOptionDialog(MainApplication.getMap().mapView,
                        GuiConfig.getInstance().getInfoClusterTxt(), GuiConfig.getInstance().getInfoClusterTitle(),
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                final boolean flag = val == JOptionPane.YES_OPTION;
                PrefManager.getInstance().saveSuppressClusterInfoFlag(flag);
                isDisplayed = false;
            }
        }
    }
}
