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
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * Defines a custom check box controller. The check box value is represented by an icon.
 *
 * @author Beata
 * @version $Revision$
 */
public class IconRadioButton extends JPanel {

    private static final long serialVersionUID = -3568998777956214141L;

    private final JRadioButton cbbox;
    private final JLabel lblIcon;

    /**
     * Builds a new {@code IconRadioButton} with the given arguments.
     *
     * @param icon the icon to be displayed as the value of the radio button
     * @param bgColor the background color
     * @param tooltip the tool-tip to display
     */
    public IconRadioButton(final Icon icon, final Color bgColor, final String tooltip) {
        this(bgColor);
        setIcon(icon);
        lblIcon.setToolTipText(tooltip);
    }

    private IconRadioButton(final Color bgColor) {
        super(new FlowLayout(FlowLayout.LEFT));
        setBackground(bgColor);
        cbbox = new JRadioButton();
        cbbox.setBackground(bgColor);
        lblIcon = new JLabel();
        add(cbbox);
        add(lblIcon);
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

    /**
     * Returns the state of the check box.
     *
     * @return true if the check box is selected, false otherwise
     */
    public boolean isSelected() {
        return cbbox.isSelected();
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
        cbbox.setSelected(selected);
    }
}