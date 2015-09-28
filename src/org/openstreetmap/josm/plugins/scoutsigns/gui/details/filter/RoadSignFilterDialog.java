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

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.UIManager;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.ModalDialog;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;


/**
 * Dialog window that displays the road sign filters.
 *
 * @author Beata
 * @version $Revision$
 */
public class RoadSignFilterDialog extends ModalDialog {

    private static final long serialVersionUID = 7883099145424623783L;

    private static final Dimension DIM = new Dimension(410, 392);
    private static final Dimension DIM_NIMBUS = new Dimension(410, 410);
    private RoadSignFilterPanel pnlFilter;


    /**
     * Builds a new {@code RoadSignFilterDialog}
     */
    public RoadSignFilterDialog() {
        super(GuiConfig.getInstance().getDlgFilterTitle(), IconConfig.getInstance().getFilterIcon().getImage());
        createComponents();
        final Dimension dim = UIManager.getLookAndFeel().getName().contains("Nimbus") ? DIM_NIMBUS : DIM;
        setSize(dim);
        setMinimumSize(dim);
    }


    @Override
    protected void createComponents() {
        pnlFilter = new RoadSignFilterPanel();
        add(pnlFilter, BorderLayout.CENTER);
        add(new ButtonPanel(this), BorderLayout.SOUTH);
    }

    /**
     * Resets the search filters to the default ones.
     */
    void resetFilters() {
        pnlFilter.resetFilters();
    }

    /**
     * Returns the selected search filters.
     *
     * @return {@code SearchFilter} object
     */
    SearchFilter selectedFilters() {
        return pnlFilter.selectedFilters();
    }
}