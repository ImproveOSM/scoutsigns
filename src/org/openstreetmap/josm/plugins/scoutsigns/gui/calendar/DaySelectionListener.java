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
 * Created on Sep 24, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.calendar;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;


/**
 * Listener observing calendar day selection events.
 * 
 * @author Beata
 * @version $Revision$
 */
class DaySelectionListener implements MouseListener {
    
    private PopupMenu popupMenu;
    private JLabel lblDay;
    
    /**
     * Builds a new day selection listener with the given arguments.
     * 
     * @param popup the {@code PopupMenu} containing the calendar days
     * @param lblDay the label representing a day from a month
     */
    DaySelectionListener(PopupMenu popupMenu, JLabel lblDay) {
        this.popupMenu = popupMenu;
        this.lblDay = lblDay;
    }
    
    
    @Override
    public void mouseReleased(MouseEvent event) {
        lblDay.setOpaque(false);
        lblDay.setBackground(Color.white);
        lblDay.setForeground(Color.black);
        int day = Integer.parseInt(lblDay.getText());
        popupMenu.setDay(day);
    }
    
    @Override
    public void mouseEntered(MouseEvent event) {
        lblDay.setOpaque(true);
        lblDay.setBackground(Color.blue);
        lblDay.setForeground(Color.white);
    }
    
    @Override
    public void mouseExited(MouseEvent event) {
        lblDay.setOpaque(false);
        lblDay.setBackground(Color.white);
        lblDay.setForeground(Color.black);
    }
    
    @Override
    public void mouseClicked(MouseEvent event) {
        // no implementation
    }
    
    @Override
    public void mousePressed(MouseEvent event) {
        // no implementation
    }
}