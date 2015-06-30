/*
 * Copyright (c) 2015, skobbler GmbH
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
 * Created on May 27, 2015 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 *
 * @author Beata
 * @version $Revision$
 */
class TypeRadioButton extends JPanel {

    private static final long serialVersionUID = -4108453316110846645L;
    private final JRadioButton rbSelection;
    private final JLabel lblIcon;
    private final String type;

    /**
     * Builds a new {@code TypeRadioButton} object.
     *
     * @param type the road sign's type
     */
    TypeRadioButton(final String type, final ImageIcon icon) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.type = type;

        setBackground(Color.white);
        rbSelection = new JRadioButton();
        rbSelection.setBackground(Color.white);
        lblIcon = new JLabel();
        add(rbSelection);
        add(lblIcon);
        setIcon(icon);
        lblIcon.setToolTipText(this.type);
    }


    /**
     * Returns the icon.
     *
     * @return a {@code Icon}
     */
    public Icon getIcon() {
        return lblIcon.getIcon();
    }

    public JRadioButton getRbSelection() {
        return rbSelection;
    }

    /**
     * Returns the state of the check box.
     *
     * @return true if the check box is selected, false otherwise
     */
    public boolean isSelected() {
        return rbSelection.isSelected();
    }

    /**
     * Sets the icon.
     *
     * @param icon a {@code Icon}
     */
    public void setIcon(final Icon icon) {
        lblIcon.setIcon(icon);
    }

    /**
     * Sets the state of the check box.
     *
     * @param selected the new state
     */
    public void setSelected(final boolean selected) {
        rbSelection.setSelected(selected);
    }

    String getType() {
        return type;
    }
}