/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.observer.TripViewObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TltConfig;
import org.openstreetmap.josm.tools.Shortcut;


/**
 * Displays the details related to a selected road sign in a {@code ToggleDialog} window.
 *
 * @author Bea
 * @version $Revision$
 */
public class ScoutSignsDetailsDialog extends ToggleDialog {

    private static final long serialVersionUID = -4603746238296761716L;

    /** the toggle dialog window height */
    private static final int DLG_HEIGHT = 50;

    /** the shortcut which will be shown on the left side of JOSM */
    private static Shortcut sh = Shortcut.registerShortcut(GuiConfig.getInstance().getDlgDetailsTitle(), TltConfig
            .getInstance().getPluginTlt(), KeyEvent.VK_F, Shortcut.ALT_SHIFT);

    private final DetailsPanel pnlDetails;
    private final ButtonPanel pnlBtn;


    /**
     * Builds a new {@code SkoSignsDetailsDialog} window with the default settings.
     */
    public ScoutSignsDetailsDialog() {
        super(GuiConfig.getInstance().getDlgDetailsTitle(), IconConfig.getInstance().getShcName(), TltConfig.getInstance()
                .getPluginTlt(), sh, DLG_HEIGHT);
        setPreferredSize(new Dimension(DLG_HEIGHT, DLG_HEIGHT));

        /* create & add components */
        pnlDetails = new DetailsPanel();
        pnlBtn = new ButtonPanel();
        final JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlDetails, BorderLayout.CENTER);
        pnlMain.add(pnlBtn, BorderLayout.SOUTH);
        createLayout(pnlMain, false, null);
    }


    /**
     * Enables button panel action buttons based on the given zoom level.
     *
     * @param zoom the current zoom level.
     * @param tripView specifies if a trip is displayed or not
     */
    public void enableButtons(final int zoom, final boolean tripView) {
        pnlBtn.enableButtons(zoom, tripView);
    }

    /**
     * Registers the given observer.
     *
     * @param observer a {@code StatusChangeObserver}
     */
    public void registerStatusChangeObserver(final StatusChangeObserver observer) {
        pnlBtn.registerStatusChangeObserver(observer);
    }

    /**
     * Registers the given trip view observer.
     *
     * @param observer a {@code TripViewObserver}
     */
    public void registerTripViewObserver(final TripViewObserver observer) {
        pnlBtn.registerObserver(observer);
    }

    /**
     * Updates the details panel with the given road sign.
     *
     * @param roadSign the currently selected {@code RoadSign}
     */
    public void updateData(final RoadSign roadSign) {
        synchronized (this) {
            pnlDetails.updateData(roadSign);
            pnlBtn.setRoadSign(roadSign);
            repaint();
        }
    }
}