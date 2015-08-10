package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;


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
        setName(getGuiCnf().getPnlCommentsTitle());
    }


    @Override
    void createComponents(final Collection<Comment> comments) {
        setLayout(new BorderLayout());
        final String txt = Formatter.formatComments(comments);
        final JTextPane txtPane = Builder.buildTextPane(txt, CONTENT_TYPE);
        final JScrollPane cmp =
                Builder.buildScrollPane(getGuiCnf().getPnlCommentsTitle(), txtPane, getBackground(), null);
        add(cmp, BorderLayout.CENTER);
    }
}