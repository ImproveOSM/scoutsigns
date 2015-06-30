/*
 * Copyright (c) 2015, skobbler GmbH
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
 * Created on May 27, 2015 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 *
 * @author Beata
 * @version $Revision$
 */
class TypeFilterPanel extends JPanel {

    private static final long serialVersionUID = 4445057324599948957L;

    private static final int ROW = 0;
    private static final int COL = 4;
    private static final Dimension DIM = new Dimension(100, 200);

    private ButtonGroup group;
    private final List<TypeRadioButton> buttons = new ArrayList<>();


    /**
     * Builds a new {@code TypeFilterPanel} object.
     *
     * @param selection the selected type
     */
    TypeFilterPanel(final Map<String, ImageIcon> typesMap, final String selection) {
        setLayout(new GridLayout(ROW, COL));
        setSize(DIM);
        addComponents(typesMap);
        selectElement(selection);
        setBackground(Color.white);
    }

    /**
     * Clears previous selections.
     */
    void clearSelection() {
        group.clearSelection();
        repaint();
    }


    /**
     * Returns the selected type.
     *
     * @return a string
     */
    String getSelection() {
        String selection = null;
        for (final TypeRadioButton btn : buttons) {
            if (btn.getRbSelection().isSelected()) {
                selection = btn.getType();
                break;
            }
        }
        return selection;
    }

    void update(final Map<String, ImageIcon> typesMap) {
        removeAll();
        addComponents(typesMap);
    }

    private void addComponents(final Map<String, ImageIcon> typesMap) {
        group = new ButtonGroup();
        for (final Map.Entry<String, ImageIcon> entry : typesMap.entrySet()) {
            final TypeRadioButton btn = new TypeRadioButton(entry.getKey(), entry.getValue());
            buttons.add(btn);
            group.add(btn.getRbSelection());
            add(btn);
        }
    }

    private void selectElement(final String selection) {
        if (selection != null) {
            for (final TypeRadioButton btn : buttons) {
                if (btn.getType().equals(selection)) {
                    btn.setSelected(true);
                    break;
                }
            }
        }
    }
}