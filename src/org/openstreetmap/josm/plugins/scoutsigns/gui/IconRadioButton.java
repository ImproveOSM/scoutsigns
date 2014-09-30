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
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * Defines a custom check box controller. The check box value is represented by
 * an icon.
 * 
 * @author Beata
 * @version $Revision$
 */
public class IconRadioButton extends JPanel {
    
    private static final long serialVersionUID = -3568998777956214141L;
    
    private JRadioButton cbbox;
    private JLabel lblIcon;
    
    private IconRadioButton(Color bgColor) {
        super(new FlowLayout(FlowLayout.LEFT));
        setBackground(bgColor);
        cbbox = new JRadioButton();
        cbbox.setBackground(bgColor);
        lblIcon = new JLabel();
        add(cbbox);
        add(lblIcon);
    }
    
    /**
     * Builds a new {@code IconRadioButton} with the given arguments.
     * 
     * @param icon the icon to be displayed as the value of the radio button
     * @param bgColor the background color
     * @param tooltip the tool-tip to display
     */
    public IconRadioButton(Icon icon, Color bgColor, String tooltip) {
        this(bgColor);
        setIcon(icon);
        lblIcon.setToolTipText(tooltip);
    }
    
    /**
     * Returns the state of the check box.
     * 
     * @return true if the check box is selected, false otherwise
     */
    public boolean isSelected() {
        return cbbox.isSelected();
    }
    
    /**
     * Sets the state of the check box.
     * 
     * @param selected the new state
     */
    public void setSelected(boolean selected) {
        cbbox.setSelected(selected);
    }
    
    /**
     * Sets the icon.
     * 
     * @param icon a {@code Icon}
     */
    public void setIcon(Icon icon) {
        lblIcon.setIcon(icon);
    }
    
    /**
     * Returns the icon.
     * 
     * @return a {@code Icon}
     */
    public Icon getIcon() {
        return lblIcon.getIcon();
    }
    
    public JRadioButton getRadioButton() {
        return cbbox;
    }
}