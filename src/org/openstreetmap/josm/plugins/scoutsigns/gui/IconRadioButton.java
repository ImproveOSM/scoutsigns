/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
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