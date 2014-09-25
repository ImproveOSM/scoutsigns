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
 * Created on Sep 22, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.calendar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.ComboPopup;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateUtil;


/**
 * Calendar combo box popup.
 * 
 * @author Beata
 * @version $Revision$
 */
class CalendarPopup implements ComboPopup, MouseMotionListener, MouseListener,
        KeyListener, PopupMenuListener, DaySelectionObserver {
    
    private CalendarComboBox comboBox;
    private JList<String> list = new JList<String>();
    private PopupMenu popupMenu;
    
    private boolean mouseInside = false;
    private boolean hideNext = false;
    
    
    /**
     * This is the constructor method of DatePopup class
     * @param comboBox - ComboBoxUI comboBox variable
     */
    public CalendarPopup(CalendarComboBox comboBox) {
        this.comboBox = comboBox;
        popupMenu = new PopupMenu();
        popupMenu.registerObserver(this);
    }
    
    
    @Override
    public void setDay(Date date) {
        String day = DateUtil.formatDay(date);
        comboBox.setSelectedItem(day);
        hide();
    }
    
    @Override
    public void show() {
        if (comboBox.getSelectedItem() != null) {
            popupMenu.setTime(comboBox.getSelectedItem().toString());
        }
        popupMenu.update();
        popupMenu.show(comboBox, 0, comboBox.getHeight());
    }
    
    @Override
    public void hide() {
        popupMenu.setVisible(false);
    }
    
    @Override
    public JList<String> getList() {
        return list;
    }
    
    @Override
    public MouseListener getMouseListener() {
        return this;
    }
    
    @Override
    public MouseMotionListener getMouseMotionListener() {
        return this;
    }
    
    @Override
    public KeyListener getKeyListener() {
        return this;
    }
    
    @Override
    public boolean isVisible() {
        return popupMenu.isVisible();
    }
    
    @Override
    public void uninstallingUI() {
        popupMenu.removePopupMenuListener(this);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        // no specific action
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        // no specific action
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e))
            return;
        if (!comboBox.isEnabled())
            return;
        if (comboBox.isEditable()) {
            comboBox.getEditor().getEditorComponent().requestFocus();
        } else {
            comboBox.requestFocus();
        }
        togglePopup();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        mouseInside = true;
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        mouseInside = false;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        // no specific action
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // no specific action
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // no specific action
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // no specific action
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE
                || e.getKeyCode() == KeyEvent.VK_ENTER) {
            togglePopup();
        }
    }
    
    
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        // no specific action
    }
    
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        hideNext = mouseInside;
    }
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        // no specific action
    }
    
    private void togglePopup() {
        if (isVisible() || hideNext) {
            hide();
        } else {
            show();
        }
        hideNext = false;
    }
}