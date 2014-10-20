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
 * Created on Sep 30, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;


/**
 * Displays a menu with the following road sign related action: Solve,
 * Invalidate, Duplicate and Reopen. This menu is displayed only if a road sign
 * is selected.
 * 
 * @author Beata
 * @version $Revision$
 */
class EditPopupMenu extends JPopupMenu {
    
    private static final long serialVersionUID = 4721105642048574637L;
    
    private StatusChangeObserver statusChangeObserver;
    
    
    /**
     * Builds a new {@code EditPopupMenu} with the given argument.
     * 
     * @param status the {@code Status} of the selected road sign
     */
    EditPopupMenu(List<Status> statuses) {
        GuiCnf guiCnf = GuiCnf.getInstance();
        IconCnf iconCnf = IconCnf.getInstance();
        
        boolean enabled = statuses.contains(Status.SOLVED);
        JMenuItem itemSolve = Builder.buildMenuItem(guiCnf.getTxtMenuSolve(),
                iconCnf.getSolvedIcon(), new SelectionListener(Status.SOLVED,
                        guiCnf.getDlgSolveTitle(), iconCnf.getSolvedIcon()),
                        enabled);
        add(itemSolve);
        
        enabled = statuses.contains(Status.INVALID);
        JMenuItem itemInvalidate = Builder.buildMenuItem(guiCnf.getTxtMenuInvalid(),
                iconCnf.getInvalidIcon(), new SelectionListener(Status.INVALID, 
                        guiCnf.getDlgInvalidTitle(), iconCnf.getInvalidIcon()),
                        enabled);
        add(itemInvalidate);
        
        enabled = statuses.contains(Status.DUPLICATE);
        JMenuItem itemDuplicate = Builder.buildMenuItem(guiCnf.getTxtMenuDuplicate(), 
                iconCnf.getDuplicateIcon(), new SelectionListener(Status.DUPLICATE, 
                        guiCnf.getDlgDuplicateTitle(), iconCnf.getDuplicateIcon()), 
                        enabled);
        add(itemDuplicate);
        
        enabled = statuses.contains(Status.OPEN);
        JMenuItem itemReopen = Builder.buildMenuItem(guiCnf.getTxtMenuReopen(), 
                iconCnf.getOpenIcon(), new SelectionListener(Status.OPEN,
                        guiCnf.getDlgReopenTitle(), iconCnf.getOpenIcon()), 
                        enabled);
        add(itemReopen);
    }
    
    /**
     * Registers the status change observer.
     * 
     * @param observer a {@code StatusChangeObserver}
     */
    void registerStatusChangeObserver(StatusChangeObserver observer) {
        statusChangeObserver = observer;
    }
    
    
    /*
     * Menu item selection listener.
     */
    private final class SelectionListener implements MouseListener {
        
        private String title;
        private ImageIcon image;
        private Status status;
        
        
        private SelectionListener(Status status, String title, ImageIcon image) {
            this.title = title;
            this.image = image;
            this.status = status;
        }
        
        @Override
        public void mouseReleased(MouseEvent event) {
            EditDialog dlgComment = new EditDialog(status, title, image.getImage());
            dlgComment.registerObserver(statusChangeObserver);
            dlgComment.setVisible(true);
        }
        
        @Override
        public void mouseClicked(MouseEvent event) {
            // no implementation provided
        }
        
        @Override
        public void mousePressed(MouseEvent event) {
            // no implementation provided
        }
        
        @Override
        public void mouseEntered(MouseEvent event) {
            // no implementation provided
        }
        
        @Override
        public void mouseExited(MouseEvent event) {
            // no implementation provided
        }
    }
}