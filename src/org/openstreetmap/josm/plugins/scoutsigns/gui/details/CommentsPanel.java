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
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;
import com.telenav.josm.common.gui.GuiBuilder;


/**
 * Builds a panel for displaying the {@code Comment}s of the selected road sign.
 *
 * @author Bea
 * @version $Revision$
 */
class CommentsPanel extends InfoPanel<Collection<Comment>> {

    private static final long serialVersionUID = 4341574605078192809L;
    private static final Dimension DIM = new Dimension(150, 100);
    private static final String CONTENT_TYPE = "text/html";


    /**
     * Builds a new {@code CommentsPanel}
     */
    CommentsPanel() {
        setName(getGuiCnf().getPnlCommentsTitle());
    }


    @Override
    protected void createComponents(final Collection<Comment> comments) {
        setLayout(new BorderLayout());
        final String txt = Formatter.formatComments(comments);
        final JTextPane txtPane = buildTextPane(txt, CONTENT_TYPE);
        final JScrollPane cmp = GuiBuilder.buildScrollPane(txtPane, getGuiCnf().getPnlCommentsTitle(), getBackground(),
                null, 100, false, DIM);
        add(cmp, BorderLayout.CENTER);
    }

    /**
     * Builds a {@code JTextPane} with the given text and content type.
     *
     * @param txt the text to be displayed in the text component
     * @param contentType the text's content type
     * @return a {@code JTextPane}
     */
    private JTextPane buildTextPane(final String txt, final String contentType) {
        final JTextPane txtPane = new JTextPane();
        txtPane.setCaretPosition(0);
        txtPane.setEditable(false);
        txtPane.setContentType(contentType);
        txtPane.setText(txt);
        return txtPane;
    }
}