/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.verifier;

import javax.swing.JComponent;
import javax.swing.JLabel;


/**
 * Input verifier for the duplicate id text field.
 *
 * @author Beata
 * @version $Revision$
 */
public class DuplicateIdVerifier extends AbstractVerifier {

    private static final int MIN_VAL = 0;

    private final boolean required;

    /**
     * Builds a {@code DuplicateIdVerifier} object with the given arguments.
     *
     * @param component the {@code JComponent} that is validated
     * @param lblMessage a {@code JLabel} to be displayed if the user input is invalid. This string is displayed as a
     * tool-tip.
     */
    public DuplicateIdVerifier(final JComponent component, final JLabel lblMessage) {
        super(component, lblMessage);
        this.required = true;
    }

    /**
     * Builds a {@code DuplicateIdVerifier} object with the given arguments.
     *
     * @param component the {@code JComponent} that is validated
     * @param message a {@code String} to be displayed if the user input is invalid. This string is displayed as a
     * tool-tip.
     */
    public DuplicateIdVerifier(final JComponent component, final String message) {
        super(component, message);
        this.required = false;
    }


    @Override
    boolean validate(final String value) {
        boolean valid = !required;
        if (value != null && !value.isEmpty()) {
            try {
                final Long longValue = Long.parseLong(value);
                valid = longValue > MIN_VAL;
            } catch (final NumberFormatException e) {
                valid = false;
            }
        }
        return valid;
    }
}