/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import org.openstreetmap.josm.tools.GuiSizesHelper;
import com.telenav.josm.common.gui.CancelAction;
import com.telenav.josm.common.gui.ModalDialog;
import com.telenav.josm.common.gui.builder.ButtonBuilder;
import com.telenav.josm.common.gui.builder.ContainerBuilder;


/**
 * Dialog window that displays the road sign filters.
 *
 * @author Beata
 * @version $Revision$
 */
public class RoadSignFilterDialog extends ModalDialog {

    private static final long serialVersionUID = 7883099145424623783L;

    private static final Dimension DIM = new Dimension(460, 410);
    private RoadSignFilterPanel pnlFilter;


    /**
     * Builds a new {@code RoadSignFilterDialog}
     */
    public RoadSignFilterDialog() {
        super(GuiConfig.getInstance().getDlgFilterTitle(), IconConfig.getInstance().getFilterIcon().getImage(),
                GuiSizesHelper.getDimensionDpiAdjusted(DIM));
        setLocationRelativeTo(MainApplication.getMap().mapView);
        createComponents();
    }


    @Override
    protected void createComponents() {
        pnlFilter = new RoadSignFilterPanel();
        add(pnlFilter, BorderLayout.CENTER);
        final JButton btnReset = ButtonBuilder.build(new ResetAction(), GuiConfig.getInstance().getBtnReset());
        final JButton btnOk = ButtonBuilder.build(new OkAction(), GuiConfig.getInstance().getBtnOk());
        final JButton btnCancel = ButtonBuilder.build(new CancelAction(this), GuiConfig.getInstance().getBtnCancel());
        final JPanel pnlButton = ContainerBuilder.buildFlowLayoutPanel(FlowLayout.RIGHT, btnReset, btnOk, btnCancel);
        add(pnlButton, BorderLayout.SOUTH);
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


    private final class ResetAction extends AbstractAction {

        private static final long serialVersionUID = -5471397013943973646L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            pnlFilter.resetFilters();
        }
    }


    private final class OkAction extends AbstractAction {


        private static final long serialVersionUID = -1157361468543031995L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            final PrefManager prefManager = PrefManager.getInstance();
            final SearchFilter newFilter = pnlFilter.selectedFilters();
            if (newFilter != null) {
                final SearchFilter oldFilter = prefManager.loadSearchFilter();
                if (oldFilter.equals(newFilter)) {
                    prefManager.saveFiltersChangedFlag(false);
                } else {
                    prefManager.saveSearchFilter(newFilter);
                    prefManager.saveFiltersChangedFlag(true);
                }
                dispose();
            }
        }
    }
}