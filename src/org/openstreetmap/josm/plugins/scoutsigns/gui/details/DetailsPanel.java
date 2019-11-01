/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.Dimension;
import java.util.Collection;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import org.openstreetmap.josm.plugins.scoutsigns.entity.CarPosition;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Trip;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import com.telenav.josm.common.gui.builder.ContainerBuilder;


/**
 * Builds a tabbed pane displaying the details of the selected {@code RoadSign}.
 *
 * @author Bea
 * @version $Revision$
 */
class DetailsPanel extends JTabbedPane {

    private static final long serialVersionUID = -2257889142891757874L;

    /** the preferred dimension of the panel components */
    private static final Dimension DIM = new Dimension(150, 100);
    private static final int SCROLL_BAR_UNIT = 100;

    /* panel components */
    private final RoadSignPanel pnlRoadSign;
    private final CarPositionPanel pnlCarPos;
    private final TripPanel pnlTrip;
    private final CommentsPanel pnlComments;


    /**
     * Builds a new {@code DetailsPanel}.
     */
    DetailsPanel() {
        setIgnoreRepaint(true);

        // create & add components
        pnlRoadSign = new RoadSignPanel();
        pnlTrip = new TripPanel();
        pnlCarPos = new CarPositionPanel();
        pnlComments = new CommentsPanel();
        final GuiConfig guiCnf = GuiConfig.getInstance();
        final JScrollPane cmpRoadSign = ContainerBuilder.buildScrollPane(pnlRoadSign, guiCnf.getPnlRoadSignTitle(),
                getBackground(), null, SCROLL_BAR_UNIT, false, DIM);
        add(cmpRoadSign);
        final JScrollPane cmpCarLocation = ContainerBuilder.buildScrollPane(pnlCarPos, guiCnf.getPnlCarPosTitle(),
                getBackground(), null, SCROLL_BAR_UNIT, false, DIM);
        add(cmpCarLocation);
        final JScrollPane cmpTrip = ContainerBuilder.buildScrollPane(pnlTrip, guiCnf.getPnlTripTitle(), getBackground(),
                null, 100, false, DIM);
        add(cmpTrip);
        add(pnlComments);
    }


    /**
     * Updates the details panel with the given road sign.
     *
     * @param roadSign a {@code RoadSign}
     */
    void updateData(final RoadSign roadSign) {
        pnlRoadSign.updateData(roadSign);
        CarPosition carPos = null;
        Collection<Comment> comments = null;
        Trip trip = null;
        if (roadSign != null) {
            carPos = roadSign.getCarPos();
            trip = roadSign.getTrip();
            comments = roadSign.getComments();
        }
        pnlCarPos.updateData(carPos);
        pnlTrip.updateData(trip);
        pnlComments.updateData(comments);
    }
}