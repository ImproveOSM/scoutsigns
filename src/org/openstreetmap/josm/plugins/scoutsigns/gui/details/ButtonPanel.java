/*
 * Copyright (c) 2014 SKOBBLER SRL.
 * Cuza Voda 1, Cluj-Napoca, Cluj, 400107, Romania
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL 
 * ("Confidential Information"). You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the license 
 * agreement you entered into with SKOBBLER SRL.
 * 
 * Created on Jul 29, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter.RoadSignFilterDialog;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TltCnf;


/**
 * Defines the button panel for the road sign details dialog.
 * 
 * @author Bea
 * @version $Revision$
 */
class ButtonPanel extends JPanel {
    
    private static final long serialVersionUID = -853684446082269916L;
    private static final Dimension DIM = new Dimension(200,23);
    
    private static final int ROWS = 1;
    private static final int COLS = 5;
    
    /* the selected road sign */
    private RoadSign roadSign;
    private ImageFrame imgFrame;
    
    private StatusChangeObserver statusChangeObserver;
    
    /* the list of statuses that a road sign/ set of road signs might have */
    private List<Status> statuses = new ArrayList<>(Status.VALUES_LIST);
    
    
    /**
     * Builds a new {@code ButtonPanel}
     */
    ButtonPanel() {
        super(new GridLayout(ROWS, COLS));
        
        // add components
        IconCnf iconCnf = IconCnf.getInstance();
        TltCnf tltCnf = TltCnf.getInstance();
        add(Builder.buildButton(new DisplayFilterDialog(),
                iconCnf.getFilterIcon(), tltCnf.getBtnFilter()));
        add(Builder.buildButton(new DisplayImageFrame(), iconCnf.getPhotoIcon(), 
                tltCnf.getBtnPhoto()));
        add(Builder.buildButton(null, iconCnf.getTripIcon(), tltCnf.getBtnTrip()));
        add(Builder.buildButton(new DisplayCommentDialog(),
                iconCnf.getCommentIcon(), tltCnf.getBtnComment()));
        add(Builder.buildButton(new DisplayEditMenu(),
                iconCnf.getMoreActionIcon(), tltCnf.getBtnMoreAction()));
        setPreferredSize(DIM);
    }
    
    
    /**
     * Sets the selected road sign.
     * 
     * @param roadSign a {@code RoadSign}
     */
    void setRoadSign(RoadSign roadSign) {
        this.roadSign = roadSign;
        if (this.roadSign == null) {
            // restore possible statuses
            if (statuses.size() != Status.VALUES_LIST.size()) {
                statuses = new ArrayList<>(Status.VALUES_LIST);
            }
        } else {
            statuses.remove(this.roadSign.getStatus());
        }
    }
    
    /**
     * Registers the given observer.
     * 
     * @param observer a {@code StatusChangeObserver}
     */
    void registerStatusChangeObserver(StatusChangeObserver observer) {
        statusChangeObserver = observer;
    }
    
    
    /*
     * Displays the selected road sign's image.
     */
    private final class DisplayImageFrame extends AbstractAction {
        
        private static final long serialVersionUID = 5500399753585606903L;
        
        @Override
        public void actionPerformed(ActionEvent event) {
            if (roadSign != null) {
                if (imgFrame != null && imgFrame.isVisible()) {
                    imgFrame.dispose();
                }
                imgFrame = new ImageFrame(roadSign.getImage());
                imgFrame.pack();
            }
        }
    }
    
    
    /*
     * Displays the filter dialog window.
     */
    private final class DisplayFilterDialog extends AbstractAction {
        
        private static final long serialVersionUID = -7084091586699723933L;
        
        @Override
        public void actionPerformed(ActionEvent event) {
            RoadSignFilterDialog dlgFilter = new RoadSignFilterDialog();
            dlgFilter.setVisible(true);
        }
    }
    
    /*
     * Displays the comment dialog window.
     */
    private final class DisplayCommentDialog extends AbstractAction {
        
        private static final long serialVersionUID = -2470311157850355646L;
        
        @Override
        public void actionPerformed(ActionEvent event) {
            if (roadSign != null) {
                EditDialog dlgComment = new EditDialog(null, 
                        GuiCnf.getInstance().getDlgCommentTitle(), 
                        IconCnf.getInstance().getCommentIcon().getImage());
                dlgComment.registerObserver(statusChangeObserver);
                dlgComment.setVisible(true);
            }
        }
    }
    
    /*
     * Displays the edit menu.
     */
    private final class DisplayEditMenu extends AbstractAction {
        
        private static final long serialVersionUID = 5945560671001154104L;
        
        @Override
        public void actionPerformed(ActionEvent event) {
            if (roadSign != null) {
                EditPopupMenu editMenu = new EditPopupMenu(statuses);
                editMenu.registerStatusChangeObserver(statusChangeObserver);
                editMenu.show(ButtonPanel.this, 0, 0);
                Point point = getComponent(getComponentCount() - 1).
                        getLocationOnScreen();
                editMenu.setLocation(point.x, point.y - getHeight());
            }
        }
    }
}