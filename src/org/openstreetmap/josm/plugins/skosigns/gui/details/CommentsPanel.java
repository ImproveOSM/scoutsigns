/*
 * Copyright (c) 2014 SKOBBLER SRL.
 * Cuza Voda 1, Cluj-Napoca, Cluj, 400107, Romania
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL 
 * ("Confidential Information"). You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the license 
 * agreement you entered into with SKOBBLER SRL.
 * 
 * Created on Jul 29, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.skosigns.gui.details;

import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.openstreetmap.josm.plugins.skosigns.entity.Comment;
import org.openstreetmap.josm.plugins.skosigns.gui.Builder;
import org.openstreetmap.josm.plugins.skosigns.gui.Formatter;


/**
 * Builds a panel for displaying the {@code Comment}s of the selected road sign.
 * 
 * @author Bea
 * @version $Revision$
 */
class CommentsPanel extends InfoPanel<Collection<Comment>> {
    
    private static final long serialVersionUID = 4341574605078192809L;
    private static final String CONTENT_TYPE = "text/html";
    
    
    /**
     * Builds a new {@code CommentsPanel}
     */
    CommentsPanel() {
        super(getGuiCnf().getDetailsDefLbl());
        setName(getGuiCnf().getCommentsTitle());
    }
    
    
    @Override
    void createComponents(Collection<Comment> comments) {
        setLayout(new BorderLayout());
        String txt = Formatter.formatComments(comments);
        JTextPane txtPane = Builder.buildTextPane(txt, CONTENT_TYPE);
        JScrollPane cmp = Builder.buildScrollPane(getGuiCnf().getCommentsTitle(),
                txtPane, getBackground(), null);
        add(cmp, BorderLayout.CENTER);
    }
}