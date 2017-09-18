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
