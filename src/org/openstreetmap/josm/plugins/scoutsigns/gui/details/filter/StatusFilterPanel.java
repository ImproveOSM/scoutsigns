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
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;


/**
 * A custom panel displaying the road sign status filters.
 * 
 * @author Beata
 * @version $Revision$
 */
class StatusFilterPanel extends JPanel {
    
    private static final long serialVersionUID = -4882107014928534768L;
    
    private static final GridBagConstraints CB_OPEN = new GridBagConstraints(1,
            1, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0);
    private static final GridBagConstraints CB_SOLVED = new GridBagConstraints(
            2, 1, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0);
    private static final GridBagConstraints CB_INVALID =
            new GridBagConstraints(3, 1, 1, 1, 1, 0,
                    GridBagConstraints.PAGE_START,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0);
    private static final GridBagConstraints CB_DUPL = new GridBagConstraints(4,
            1, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0);
    
    private JRadioButton cboxOpen;
    private JRadioButton cboxSolved;
    private JRadioButton cboxInvalid;
    private JRadioButton cboxDuplicate;
    private ButtonGroup btnGroup;
    
    
    /**
     * Builds a new status filter panel with the given argument.
     * 
     * @param selection {@code Status} to be selected
     */
    StatusFilterPanel(Status selection) {
        super(new GridBagLayout());
        btnGroup = new ButtonGroup();
        addComponents();
        selectElement(selection);
    }
    
    
    private void addComponents() {
        cboxOpen = Builder.buildRadioButton(Status.OPEN.toString().toLowerCase(),
                FontUtil.PLAIN_12, getBackground());
        btnGroup.add(cboxOpen);
        add(cboxOpen, CB_OPEN);
        
        cboxSolved = Builder.buildRadioButton(Status.SOLVED.toString().toLowerCase(),
                FontUtil.PLAIN_12, getBackground());
        btnGroup.add(cboxSolved);
        add(cboxSolved, CB_SOLVED);
        
        cboxInvalid = Builder.buildRadioButton(Status.INVALID.toString().
                toLowerCase(), FontUtil.PLAIN_12, getBackground());
        btnGroup.add(cboxInvalid);
        add(cboxInvalid, CB_INVALID);
        
        cboxDuplicate = Builder.buildRadioButton(Status.DUPLICATE.toString().
                toLowerCase(), FontUtil.PLAIN_12, getBackground());
        btnGroup.add(cboxDuplicate);
        add(cboxDuplicate, CB_DUPL);
    }
    
    private void selectElement(Status selection) {
        if (selection != null) {
            switch (selection) {
                case OPEN:
                    cboxOpen.setSelected(true);
                    break;
                case SOLVED:
                    cboxSolved.setSelected(true);
                    break;
                case INVALID:
                    cboxInvalid.setSelected(true);
                    break;
                default:
                    cboxDuplicate.setSelected(true);
                    break;
            }
        }
    }
    
    /**
     * Returns the selected status.
     * 
     * @return a {@code Status}
     */
    Status getSelection() {
        Status status = null;
        if (cboxOpen.isSelected()) {
            status = Status.OPEN;
        }
        if (cboxSolved.isSelected()) {
            status = Status.SOLVED;
        }
        if (cboxInvalid.isSelected()) {
            status = Status.INVALID;
        }
        if (cboxDuplicate.isSelected()) {
            status = Status.DUPLICATE;
        }
        return status;
    }
    
    /**
     * Clears the selected status elements.
     */
    void clearSelection() {
        btnGroup.clearSelection();
    }
}