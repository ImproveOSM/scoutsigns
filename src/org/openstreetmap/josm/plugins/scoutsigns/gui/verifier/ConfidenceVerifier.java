/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.verifier;

import javax.swing.JComponent;


/**
 * Input verifier for the confidence text field.
 *
 * @author Beata
 * @version $Revision$
 */
public class ConfidenceVerifier extends AbstractVerifier {

    private static final int MIN_VAL = 0;
    private static final int MAX_VAL = 300;

    /**
     * Builds a new {@code ConfidenceVerifier} with the given arguments.
     *
     * @param component the {@code JComponent} that is validated
     * @param message a {@code String} to be displayed if the user input is invalid. This string is displayed as a
     * tool-tip.
     */
    public ConfidenceVerifier(final JComponent component, final String message) {
        super(component, message);
    }


    @Override
    protected boolean validate(final String value) {
        boolean valid = true;
        if (value != null && !value.isEmpty()) {
            try {
                final Short shortValue = Short.parseShort(value);
                valid = shortValue >= MIN_VAL && shortValue <= MAX_VAL;
            } catch (final NumberFormatException e) {
                valid = false;
            }
        }
        return valid;
    }
}
