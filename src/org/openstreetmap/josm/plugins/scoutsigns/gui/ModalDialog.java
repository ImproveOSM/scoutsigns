package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JDialog;
import org.openstreetmap.josm.Main;


/**
 * Defines a general model dialog.
 *
 * @author Beata
 * @version $Revision$
 */
public abstract class ModalDialog extends JDialog {

    private static final long serialVersionUID = 1102099029345490735L;


    /**
     * Builds a new {@code ModalDialog} with the given arguments.
     *
     * @param title the dialog title
     * @param icon the icon to be displayed in the dialog header
     * @param size the size of the dialog
     */
    public ModalDialog(final String title, final Image icon, final Dimension size) {
        setLayout(new BorderLayout());
        setModal(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(Main.map.mapView);
        setTitle(title);
        setIconImage(icon);
        setSize(size);
        setMinimumSize(size);
    }


    /**
     * Creates the UI components.
     */
    protected abstract void createComponents();
}