/*
 * Copyright (c) 2015, skobbler GmbH
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
 * @author Beata
 */
package org.openstreetmap.josm.plugins.scoutsigns.util;

import javax.swing.JOptionPane;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


public final class InfoDialog {

    public void displayDialog(final int zoom, final int prevZoom) {
        final int maxZoom = ServiceCnf.getInstance().getMaxClusterZoom();
        if (!PrefManager.getInstance().loadSuppressClusterInfoFlag() && (zoom <= maxZoom && zoom < prevZoom)) {
            final int val =
                    JOptionPane.showOptionDialog(Main.map.mapView, GuiCnf.getInstance().getInfoClusterTxt(), GuiCnf
                            .getInstance().getInfoClusterTitle(), JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE, null, null, null);
            final boolean flag = val == JOptionPane.YES_OPTION;
            PrefManager.getInstance().saveSuppressClusterInfoFlag(flag);
        } else if (!PrefManager.getInstance().loadSupressMapillaryInfoFlag() && zoom < maxZoom) {
            final int val =
                    JOptionPane.showOptionDialog(Main.map.mapView, GuiCnf.getInstance().getInfoMapillaryTxt(), GuiCnf
                            .getInstance().getInfoMapillaryTitle(), JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE, null, null, null);
            final boolean flag = val == JOptionPane.YES_OPTION;
            PrefManager.getInstance().saveSuppressMapillaryInfoFlag(flag);
        }
    }
}
