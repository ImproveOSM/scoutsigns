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
 * Created on Sep 23, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;


/**
 * Defines a custom panel for displaying the road sign types.
 * 
 * @author Beata
 * @version $Revision$
 */
class TypeFilterPanel extends JPanel {
    
    private static final long serialVersionUID = 4445057324599948957L;
    
    private static final int ROW = 0;
    private static final int COL = 4;
    private static final Dimension DIM = new Dimension(100, 200);
    
    private ButtonGroup group;
    private List<TypeRadioButton> buttons = new ArrayList<>();
    
    
    /**
     * Builds a new {@code TypeFilterPanel} object.
     * 
     * @param selection the selected type
     */
    TypeFilterPanel(String selection) {
        setLayout(new GridLayout(ROW, COL));
        setSize(DIM);
        group = new ButtonGroup();
        
        addComponents();
        selectElement(selection);
        setBackground(Color.white);
    }
    
    private void addComponents() {
        for (String type : ServiceCnf.getInstance().getTypes()) {
            TypeRadioButton btn = new TypeRadioButton(type);
            buttons.add(btn);
            group.add(btn.getRadioButton());
            add(btn);
        }
    }
    
    private void selectElement(String selection) {
        if (selection != null) {
            for (TypeRadioButton btn : buttons) {
                if (btn.getType().equals(selection)) {
                    btn.setSelected(true);
                    break;
                }
            }
        }
    }
    
    /**
     * Clears previous selections.
     */
    void clearSelection() {
        group.clearSelection();
        repaint();
    }
    
    /**
     * Returns the selected type.
     * 
     * @return a string
     */
    String getSelection() {
        String selection = null;
        for (TypeRadioButton btn : buttons) {
            if (btn.getRadioButton().isSelected()) {
                selection = btn.getType();
                break;
            }
        }
        return selection;
    }
}