/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.verifier;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * Defines common functionality for validation of a {@code JTextField} UI component. Specific a {@code JTextField}
 * validators can extend this class.
 *
 * @author Beata
 * @version $Revision$
 */
abstract class AbstractVerifier extends InputVerifier implements KeyListener {

    private final JComponent component;
    private String message;
    private JLabel lblMessage;


    /**
     * Builds a new object based on the given arguments.
     *
     * @param component the {@code JComponent} that is validated
     * @param lblMessage a {@code JLabel} to be displayed if the user input is invalid
     */
    public AbstractVerifier(final JComponent component, final JLabel lblMessage) {
        this(component);
        this.lblMessage = lblMessage;
    }

    /**
     * Builds a new object based on the given arguments.
     *
     * @param component the {@code JComponent} that is validated
     * @param message a {@code String} to be displayed if the user input is invalid. This string is displayed as a
     * tool-tip.
     */
    public AbstractVerifier(final JComponent component, final String message) {
        this(component);
        this.message = message;
    }

    private AbstractVerifier(final JComponent component) {
        this.component = component;
        this.component.addKeyListener(this);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            verify(component);
        } else {
            component.setBackground(Color.white);
            component.setToolTipText(null);
            if (lblMessage != null) {
                lblMessage.setVisible(false);
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        // not supported
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        // not supported
    }

    @Override
    public boolean verify(final JComponent component) {
        final String valueStr = ((JTextField) component).getText().trim();

        boolean valid;
        if (!validate(valueStr)) {
            if (lblMessage != null) {
                lblMessage.setVisible(true);
            } else {
                component.setBackground(Color.pink);
                component.setToolTipText(message);
            }
            valid = false;
        } else {
            component.setBackground(Color.white);
            if (lblMessage != null) {
                lblMessage.setVisible(false);
            } else {
                component.setToolTipText(null);
            }
            valid = true;
        }
        return valid;
    }

    /**
     * Validates the given value.
     *
     * @param value a {@code String} representing the user's input.
     * @return true if the value is valid, false otherwise
     */
    abstract boolean validate(String value);
}