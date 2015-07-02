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
 * Created on Jun 2, 2015 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Source;
import org.openstreetmap.josm.plugins.scoutsigns.gui.TypeIconFactory;


/**
 * Custom list cell renderer for road sign types.
 *
 * @author Beata
 * @version $Revision$
 */
final class TypeListCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = -336809624912590775L;
    private final Dimension SIZE = new Dimension(30, 30);
    private final Source source;


    /**
     * Builds a new object with the given argument.
     *
     * @param source defines the road sign's source
     */
    TypeListCellRenderer(final Source source) {
        this.source = source;
    }


    @Override
    public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
            final boolean isSelected, final boolean cellHasFocus) {
        final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        final String type = (String) value;
        label.setIcon(TypeIconFactory.getInstance().getIcon(source, type));
        label.setText("");
        label.setPreferredSize(SIZE);
        label.setToolTipText(type);
        return label;
    }
}