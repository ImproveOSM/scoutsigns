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
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
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
    
    
    /**
     * Builds a new {@code ButtonPanel}
     */
    ButtonPanel() {
        super(new GridLayout(ROWS, COLS));
        add(Builder.buildButton(null, IconCnf.getInstance().getFilterIcon()));
        add(Builder.buildButton(null, IconCnf.getInstance().getPhotoIcon()));
        add(Builder.buildButton(null, IconCnf.getInstance().getTripIcon()));
        add(Builder.buildButton(null, IconCnf.getInstance().getCommentIcon()));
        add(Builder.buildButton(null, IconCnf.getInstance().getMoreActionIcon()));
    }
}