/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TltConfig;
import com.telenav.josm.common.gui.builder.MenuBuilder;


/**
 * Displays a menu with the following road sign related action: Solve, Invalidate, Duplicate and Reopen. This menu is
 * displayed only if a road sign is selected.
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
    EditPopupMenu(final List<Status> statuses) {
        final GuiConfig guiCnf = GuiConfig.getInstance();
        final TltConfig tltCnf = TltConfig.getInstance();
        final IconConfig iconCnf = IconConfig.getInstance();

        boolean enabled = statuses.contains(Status.SOLVED);
        final JMenuItem itemSolve = MenuBuilder.build(iconCnf.getSolvedIcon(), guiCnf.getTxtMenuSolve(),
                tltCnf.getBtnSolved(),
                new SelectionListener(Status.SOLVED, guiCnf.getDlgSolveTitle(), iconCnf.getSolvedIcon()), enabled);
        add(itemSolve);

        enabled = statuses.contains(Status.INVALID);
        final JMenuItem itemInvalidate = MenuBuilder.build(iconCnf.getInvalidIcon(), guiCnf.getTxtMenuInvalid(),
                tltCnf.getBtnInvalid(),
                new SelectionListener(Status.INVALID, guiCnf.getDlgInvalidTitle(), iconCnf.getInvalidIcon()), enabled);
        add(itemInvalidate);

        enabled = statuses.contains(Status.DUPLICATE);
        final JMenuItem itemDuplicate =
                MenuBuilder.build(iconCnf.getDuplicateIcon(), guiCnf.getTxtMenuDuplicate(), tltCnf.getBtnDuplicate(),
                        new SelectionListener(Status.DUPLICATE, guiCnf.getDlgDuplicateTitle(), iconCnf.getDuplicateIcon()),
                        enabled);
        add(itemDuplicate);

        enabled = statuses.contains(Status.OPEN);
        final JMenuItem itemReopen =
                MenuBuilder.build(iconCnf.getOpenIcon(), guiCnf.getTxtMenuReopen(), tltCnf.getBtnOpen(),
                        new SelectionListener(Status.OPEN, guiCnf.getDlgReopenTitle(), iconCnf.getOpenIcon()), enabled);
        add(itemReopen);
    }

    /**
     * Registers the status change observer.
     *
     * @param observer a {@code StatusChangeObserver}
     */
    void registerStatusChangeObserver(final StatusChangeObserver observer) {
        statusChangeObserver = observer;
    }

    /*
     * Menu item selection listener.
     */
    private class SelectionListener implements MouseListener {

        private final String title;
        private final ImageIcon image;
        private final Status status;


        private SelectionListener(final Status status, final String title, final ImageIcon image) {
            this.title = title;
            this.image = image;
            this.status = status;
        }

        @Override
        public void mouseClicked(final MouseEvent event) {
            // no implementation provided
        }

        @Override
        public void mouseEntered(final MouseEvent event) {
            // no implementation provided
        }

        @Override
        public void mouseExited(final MouseEvent event) {
            // no implementation provided
        }

        @Override
        public void mousePressed(final MouseEvent event) {
            // no implementation provided
        }

        @Override
        public void mouseReleased(final MouseEvent event) {
            final EditDialog dlgComment = new EditDialog(status, title, image.getImage());
            dlgComment.registerObserver(statusChangeObserver);
            dlgComment.setVisible(true);
        }
    }
}