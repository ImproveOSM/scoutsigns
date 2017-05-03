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
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import com.telenav.josm.common.gui.BasicInfoPanel;
import com.telenav.josm.common.gui.builder.ContainerBuilder;


/**
 * Builds a panel for displaying the {@code Comment}s of the selected road sign.
 *
 * @author Bea
 * @version $Revision$
 */
class CommentsPanel extends BasicInfoPanel<Collection<Comment>> {

    private static final long serialVersionUID = 4341574605078192809L;
    private static final Dimension DIM = new Dimension(150, 100);
    private static final int SCROLL_BAR_UNIT = 100;


    /**
     * Builds a new {@code CommentsPanel}
     */
    CommentsPanel() {
        setName(GuiConfig.getInstance().getPnlCommentsTitle());
    }


    @Override
    public void createComponents(final Collection<Comment> comments) {
        setLayout(new BorderLayout());
        final CommentsList commentsList = new CommentsList(comments.toArray(new Comment[0]));
        final JScrollPane cmp = ContainerBuilder.buildScrollPane(commentsList,
                GuiConfig.getInstance().getPnlCommentsTitle(), getBackground(), null, SCROLL_BAR_UNIT, false, DIM);
        add(cmp, BorderLayout.CENTER);
    }
}