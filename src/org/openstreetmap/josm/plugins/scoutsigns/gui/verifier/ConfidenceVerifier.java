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
 * Created on Nov 23, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.verifier;

import javax.swing.JLabel;


/**
 * Input verifier for the confidence text field.
 * 
 * @author Beata
 * @version $Revision$
 */
public class ConfidenceVerifier extends JTextFieldVerifier {
    
    private static final int MIN_VAL = 0;
    private static final int MAX_VAL = 100;
    
    
    /**
     * Builds a new {@code ConfidenceVerifier} with the given argument.
     * 
     * @param lblError the {@code JLabel} to display if the user entered input
     * is not valid
     */
    public ConfidenceVerifier(JLabel lblError) {
        super(lblError);
    }
    
    
    @Override
    boolean validate(String value) {
        boolean valid = true;
        if (!value.isEmpty()) {
            try {
                Short shortValue = Short.parseShort(value);
                valid = shortValue >= MIN_VAL && shortValue <= MAX_VAL;
            } catch (NumberFormatException e) {
                valid = false;
            }
        }
        return valid;
    }
}
