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
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import com.telenav.josm.common.gui.CancelAction;
import com.telenav.josm.common.gui.GuiBuilder;


/**
 * Defines the a button panel with "Reset", "OK" and "Cancel" buttons for the road sign filter dialog.
 *
 * @author Beata
 * @version $Revision$
 */
class ButtonPanel extends JPanel {

    private class Action extends AbstractAction {

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
        final GuiConfig guiCnf = GuiConfig.getInstance();
        btnReset = GuiBuilder.buildButton(new Action(), guiCnf.getBtnReset());
        btnOk = GuiBuilder.buildButton(new Action(), guiCnf.getBtnOk());
        btnCancel = GuiBuilder.buildButton(new CancelAction(parent), guiCnf.getBtnCancel());
        add(btnReset);
        add(btnOk);
        add(btnCancel);
    }
}