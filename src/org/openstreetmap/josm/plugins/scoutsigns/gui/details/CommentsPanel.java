/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
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