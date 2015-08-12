/*
 *  Copyright 2015 Telenav, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
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