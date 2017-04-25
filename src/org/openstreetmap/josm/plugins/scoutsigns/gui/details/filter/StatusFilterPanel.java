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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import com.telenav.josm.common.gui.GuiBuilder;


/**
 * A custom panel displaying the road sign status filters.
 *
 * @author Beata
 * @version $Revision$
 */
class StatusFilterPanel extends JPanel {

    private static final long serialVersionUID = -4882107014928534768L;

    private static final GridBagConstraints CB_OPEN = new GridBagConstraints(0, 0, 1, 1, 0, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 9), 0, 0);
    private static final GridBagConstraints CB_SOLVED = new GridBagConstraints(1, 0, 1, 1, 0, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 7, 0, 5), 0, 0);
    private static final GridBagConstraints CB_INVALID = new GridBagConstraints(2, 0, 1, 1, 0, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0);
    private static final GridBagConstraints CB_DUPL = new GridBagConstraints(3, 0, 1, 1, 0, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0);

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
    StatusFilterPanel(final Status selection) {
        super(new GridBagLayout());
        addComponents();
        selectElement(selection);
    }


    private void addComponents() {
        cboxOpen = GuiBuilder.buildRadioButton(Status.OPEN.toString().toLowerCase(), GuiBuilder.FONT_SIZE_12,
                getBackground(), false);
        add(cboxOpen, CB_OPEN);
        cboxSolved = GuiBuilder.buildRadioButton(Status.SOLVED.toString().toLowerCase(), GuiBuilder.FONT_SIZE_12,
                getBackground(), false);
        add(cboxSolved, CB_SOLVED);
        cboxInvalid = GuiBuilder.buildRadioButton(Status.INVALID.toString().toLowerCase(), GuiBuilder.FONT_SIZE_12,
                getBackground(), false);
        add(cboxInvalid, CB_INVALID);
        cboxDuplicate = GuiBuilder.buildRadioButton(Status.DUPLICATE.toString().toLowerCase(), GuiBuilder.FONT_SIZE_12,
                getBackground(), false);
        add(cboxDuplicate, CB_DUPL);
        btnGroup = GuiBuilder.buildRadioButtonGroup(cboxOpen, cboxSolved, cboxInvalid, cboxDuplicate);
    }

    private void selectElement(final Status selection) {
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
     * Clears the selected status elements.
     */
    void clearSelection() {
        btnGroup.clearSelection();
    }

    void enableComponents(final boolean isEnabled) {
        cboxOpen.setEnabled(isEnabled);
        cboxSolved.setEnabled(isEnabled);
        cboxInvalid.setEnabled(isEnabled);
        cboxDuplicate.setEnabled(isEnabled);
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
}