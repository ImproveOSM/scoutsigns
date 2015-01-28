/*
 * Copyright (c) 2014, skobbler GmbH
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
 * Created on Jun 17, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.observer.TripViewObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TltCnf;
import org.openstreetmap.josm.tools.Shortcut;


/**
 * Displays the details related to a selected road sign in a
 * {@code ToggleDialog} window.
 * 
 * @author Bea
 * @version $Revision$
 */
public class ScoutSignsDetailsDialog extends ToggleDialog {
    
    private static final long serialVersionUID = -4603746238296761716L;
    
    /** the toggle dialog window height */
    private static final int DLG_HEIGHT = 50;
    
    /** the shortcut which will be shown on the left side of JOSM */
    private static Shortcut sh = Shortcut.registerShortcut(GuiCnf.getInstance()
            .getDlgDetailsTitle(), TltCnf.getInstance().getPluginTlt(), KeyEvent.VK_F,
            Shortcut.ALT_SHIFT);
    
    private DetailsPanel pnlDetails;
    private ButtonPanel pnlBtn;
    
    
    /**
     * Builds a new {@code SkoSignsDetailsDialog} window with the default
     * settings.
     */
    public ScoutSignsDetailsDialog() {
        super(GuiCnf.getInstance().getDlgDetailsTitle(), IconCnf.getInstance()
                .getShcName(), TltCnf.getInstance().getPluginTlt(), sh,
                DLG_HEIGHT);
        setPreferredSize(new Dimension(DLG_HEIGHT, DLG_HEIGHT));
        
        /* create & add components */
        pnlDetails = new DetailsPanel();
        pnlBtn = new ButtonPanel();
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlDetails, BorderLayout.CENTER);
        pnlMain.add(pnlBtn, BorderLayout.SOUTH);
        add(pnlMain);
    }
    
    
    /**
     * Updates the details panel with the given road sign.
     * 
     * @param roadSign the currently selected {@code RoadSign}
     */
    public void updateData(RoadSign roadSign) {
        pnlDetails.updateData(roadSign);
        pnlBtn.setRoadSign(roadSign);
        repaint();
    }
    
    /**
     * Enables button panel action buttons based on the given zoom level.
     * 
     * @param zoom the current zoom level.
     */
    public void enableButtons(int zoom) {
        pnlBtn.enableButtons(zoom);
    }
    
    /**
     * Registers the given observer.
     * 
     * @param observer a {@code StatusChangeObserver}
     */
    public void registerStatusChangeObserver(StatusChangeObserver observer) {
        pnlBtn.registerStatusChangeObserver(observer);
    }
    
    /**
     * Registers the given trip view observer.
     * 
     * @param observer a {@code TripViewObserver}
     */
    public void registerTripViewObserver(TripViewObserver observer) {
        pnlBtn.registerObserver(observer);
    }
}