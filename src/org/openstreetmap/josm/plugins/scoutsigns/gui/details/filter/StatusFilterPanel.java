/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import com.telenav.josm.common.gui.builder.ButtonBuilder;


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
        cboxOpen = ButtonBuilder.build(Status.OPEN.toString().toLowerCase(), Font.PLAIN, getBackground(), false);
        add(cboxOpen, CB_OPEN);
        cboxSolved = ButtonBuilder.build(Status.SOLVED.toString().toLowerCase(), Font.PLAIN, getBackground(), false);
        add(cboxSolved, CB_SOLVED);
        cboxInvalid = ButtonBuilder.build(Status.INVALID.toString().toLowerCase(), Font.PLAIN, getBackground(), false);
        add(cboxInvalid, CB_INVALID);
        cboxDuplicate =
                ButtonBuilder.build(Status.DUPLICATE.toString().toLowerCase(), Font.PLAIN, getBackground(), false);
        add(cboxDuplicate, CB_DUPL);
        btnGroup = ButtonBuilder.build(cboxOpen, cboxSolved, cboxInvalid, cboxDuplicate);
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