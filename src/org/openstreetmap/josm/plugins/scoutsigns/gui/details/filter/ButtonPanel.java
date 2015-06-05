/*
 * Copyright (c) 2014, skobbler GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Sep 24, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
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
                final SearchFilter newFilter = parent.getSelectedFilters();
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