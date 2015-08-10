package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;


/**
 * This action is invoked whenever a "Cancel" button is pressed in the in a {@code JDialog} window. As a result of this
 * action the opened dialog window will be closed.
 *
 * @author Beata
 * @version $Revision$
 */
public class CancelAction extends AbstractAction {

    private static final long serialVersionUID = -4876674356093168294L;

    private final JDialog dialog;


    /**
     * Builds a {@code CancelAction} with the given argument.
     *
     * @param dialog the dialog window for which this action is registered
     */
    public CancelAction(final JDialog dialog) {
        this.dialog = dialog;
    }


    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event != null) {
            dialog.dispose();
        }
    }
}