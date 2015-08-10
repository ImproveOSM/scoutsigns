package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.CancelAction;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


/**
 * Defines the a button panel with "Reset", "OK" and "Cancel" buttons for the road sign filter dialog.
 *
 * @author Beata
 * @version $Revision$
 */
class ButtonPanel extends JPanel {

    private final class Action extends AbstractAction {

        private static final long serialVersionUID = -5379206652290290706L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            final String actionCmd = event.getActionCommand();
            if (actionCmd.equals(btnReset.getText())) {
                // reset default settings
                parent.resetFilters();
            } else {
                // apply filters
                final PrefManager prefManager = PrefManager.getInstance();
                final SearchFilter newFilter = parent.selectedFilters();
                if (newFilter != null) {
                    final SearchFilter oldFilter = prefManager.loadSearchFilter();
                    if (oldFilter.equals(newFilter)) {
                        prefManager.saveFiltersChangedFlag(false);
                    } else {
                        prefManager.saveSearchFilter(newFilter);
                        prefManager.saveFiltersChangedFlag(true);
                    }
                    parent.dispose();
                }
            }
        }
    }

    private static final long serialVersionUID = 102915374051667032L;
    private final RoadSignFilterDialog parent;
    private JButton btnReset;
    private JButton btnOk;


    private JButton btnCancel;


    /**
     * Builds a new button panel with the given argument.
     *
     * @param parent
     */
    ButtonPanel(final RoadSignFilterDialog parent) {
        super(new FlowLayout(FlowLayout.RIGHT));
        this.parent = parent;
        addComponents();
    }


    private void addComponents() {
        final GuiCnf guiCnf = GuiCnf.getInstance();
        btnReset = Builder.buildButton(new Action(), guiCnf.getBtnReset());
        btnOk = Builder.buildButton(new Action(), guiCnf.getBtnOk());
        btnCancel = Builder.buildButton(new CancelAction(parent), guiCnf.getBtnCancel());
        add(btnReset);
        add(btnOk);
        add(btnCancel);
    }
}