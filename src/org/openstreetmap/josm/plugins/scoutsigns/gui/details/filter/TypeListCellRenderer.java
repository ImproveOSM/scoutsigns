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