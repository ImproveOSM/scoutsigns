/*
 * Copyright (c) 2014 SKOBBLER SRL.
 * Cuza Voda 1, Cluj-Napoca, Cluj, 400107, Romania
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL 
 * ("Confidential Information"). You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the license 
 * agreement you entered into with SKOBBLER SRL.
 * 
 * Created on Jul 29, 2014 by Bea
 * Modified on $DateTime$ by $Author$
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
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;


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
    
    /* panel components */
    private RoadSignPanel pnlRoadSign;
    private CarPositionPanel pnlCarPos;
    private TripPanel pnlTrip;
    private CommentsPanel pnlComments;
    
    
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
        GuiCnf guiCnf = GuiCnf.getInstance();
        JScrollPane cmpRoadSign = Builder.buildScrollPane(
                guiCnf.getPnlRoadSignTitle(), pnlRoadSign, getBackground(), DIM);
        add(cmpRoadSign);
        JScrollPane cmpCarLocation = Builder.buildScrollPane(
                guiCnf.getPnlCarPosTitle(), pnlCarPos, getBackground(), DIM);
        add(cmpCarLocation);
        JScrollPane cmpTrip = Builder.buildScrollPane(guiCnf.getPnlTripTitle(),
                pnlTrip, getBackground(), DIM);
        add(cmpTrip);
        add(pnlComments);
    }
    
    
    /**
     * Updates the details panel with the given road sign.
     * 
     * @param roadSign a {@code RoadSign}
     */
    void updateData(RoadSign roadSign) {
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