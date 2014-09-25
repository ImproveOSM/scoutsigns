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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter.RoadSignFilterDialog;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;


/**
 * Defines the button panel for the road sign details dialog.
 * 
 * @author Bea
 * @version $Revision$
 */
class ButtonPanel extends JPanel {
    
    private static final long serialVersionUID = -853684446082269916L;
    
    private static final int ROWS = 1;
    private static final int COLS = 5;
    
    /* the selected road sign */
    private RoadSign roadSign;
    
    
    /**
     * Builds a new {@code ButtonPanel}
     */
    ButtonPanel() {
        super(new GridLayout(ROWS, COLS));
        add(Builder.buildButton(new DisplayFilterDialog(), IconCnf
                .getInstance().getFilterIcon()));
        add(Builder.buildButton(new DisplayImageFrame(), IconCnf.getInstance()
                .getPhotoIcon()));
        add(Builder.buildButton(null, IconCnf.getInstance().getTripIcon()));
        add(Builder.buildButton(null, IconCnf.getInstance().getCommentIcon()));
        add(Builder.buildButton(null, IconCnf.getInstance().getMoreActionIcon()));
    }
    
    
    /**
     * Sets the selected road sign.
     * 
     * @param roadSign a {@code RoadSign}
     */
    void setRoadSign(RoadSign roadSign) {
        this.roadSign = roadSign;
    }
    
    
    /*
     * Displays the selected road sign's image.
     */
    private final class DisplayImageFrame extends AbstractAction {
        
        private static final long serialVersionUID = 5500399753585606903L;
        
        @Override
        public void actionPerformed(ActionEvent event) {
            if (roadSign != null) {
                ImageFrame imgFrame = new ImageFrame(roadSign.getImage());
                imgFrame.pack();
            }
        }
    }
    
    private final class DisplayFilterDialog extends AbstractAction {
        
        private static final long serialVersionUID = -7084091586699723933L;
        
        @Override
        public void actionPerformed(ActionEvent event) {
            RoadSignFilterDialog dlgFilter = new RoadSignFilterDialog();
            dlgFilter.setVisible(true);
        }
    }
}