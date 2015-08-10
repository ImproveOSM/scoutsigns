package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.ModalDialog;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;


/**
 * Dialog window that displays the road sign filters.
 *
 * @author Beata
 * @version $Revision$
 */
public class RoadSignFilterDialog extends ModalDialog {

    private static final long serialVersionUID = 7883099145424623783L;

    private static final Dimension DIM = new Dimension(390, 402);
    private RoadSignFilterPanel pnlFilter;


    /**
     * Builds a new {@code RoadSignFilterDialog}
     */
    public RoadSignFilterDialog() {
        super(GuiCnf.getInstance().getDlgFilterTitle(), IconCnf.getInstance().getFilterIcon().getImage(), DIM);
        createComponents();
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