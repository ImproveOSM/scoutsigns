/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
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


    @Override
    public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
            final boolean isSelected, final boolean cellHasFocus) {
        final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        final String type = (String) value;
        label.setIcon(TypeIconFactory.getInstance().getIcon(type));
        label.setText("");
        label.setPreferredSize(SIZE);
        label.setToolTipText(type);
        return label;
    }
}