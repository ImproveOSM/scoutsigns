/*
 * The code is licensed under the LGPL Version 3 license http://www.gnu.org/licenses/lgpl-3.0.en.html.
 *
 * The collected imagery is protected & available under the CC BY-SA version 4 International license.
 * https://creativecommons.org/licenses/by-sa/4.0/legalcode.
 *
 * Copyright ©2017, Telenav, Inc. All Rights Reserved
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolTip;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;
import com.telenav.josm.common.gui.ScrollableToolTip;

/**
 *
 * @author beataj
 * @version $Revision$
 */
class CommentsList extends JList<Comment> {

    private static final long serialVersionUID = 4213303203872460926L;

    /**
     * Displays the given comments in a list.
     *
     * @param comments a {@code Comment} array
     */
    CommentsList(final Comment[] comments) {
        super(comments);
        setCellRenderer(new CommentCellRenderer());
    }

    @Override
    public JToolTip createToolTip() {
        final ScrollableToolTip tip = new ScrollableToolTip(this);
        tip.setComponent(this);
        if (!getToolTipText().isEmpty()) {
            tip.setVisible(true);
        }
        return tip;
    }

    private class CommentCellRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = -7149120070205196547L;

        @Override
        public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
            final JLabel label =
                    (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            final Comment comment = (Comment) value;
            label.setText(Formatter.formatComment(comment));
            label.setFont(label.getFont().deriveFont(Font.PLAIN));
            label.setToolTipText(null);
            return label;
        }
    }
}