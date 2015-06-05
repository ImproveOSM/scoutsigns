/*
 * Copyright (c) 2014, skobbler GmbH
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
 * Created on Jan 6, 2015 by Beata
 * Modified on $DateTime$ by $Author$
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