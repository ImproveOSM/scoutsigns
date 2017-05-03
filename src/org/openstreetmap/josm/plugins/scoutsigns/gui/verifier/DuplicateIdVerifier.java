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