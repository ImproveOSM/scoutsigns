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
import java.awt.Rectangle;
import javax.swing.Icon;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.TypeIconFactory;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter.DecFormat;


/**
 * Builds a panel for displaying the details of the selected {@code RoadSign}.
 * 
 * @author Bea
 * @version $Revision$
 */
class RoadSignPanel extends InfoPanel<RoadSign> {
    
    private static final long serialVersionUID = -6001820888166694669L;
    private int y = 0;
    private int pnlWidth = 0;
    
    
    @Override
    void createComponents(RoadSign roadSign) {
        y = 0;
        pnlWidth = 0;
        int widthLbl = getMaxWidth(FontUtil.FM_BOLD_12, getGuiCnf().getIdLbl(),
                getGuiCnf().getPointLbl(), getGuiCnf().getTypeLbl(),
                getGuiCnf().getStatusLbl(), getGuiCnf().getConfLbl(),
                getGuiCnf().getCreatedLbl(), getGuiCnf().getDuplLbl());
        addId(roadSign.getId(), widthLbl);
        addPoint(roadSign.getSignPos().getPosition(), widthLbl);
        addHeight(roadSign.getSignPos().getHeight(), widthLbl);
        addType(roadSign.getType(), widthLbl);
        addStatus(roadSign.getStatus(), widthLbl);
        addCreated(roadSign.getTstamp(), widthLbl);
        addDupl(roadSign.getDuplicateOf(), widthLbl);
        int pnlHeight = y + SPACE_Y;
        setPreferredSize(new Dimension(pnlWidth + SPACE_Y, pnlHeight));
    }
    
    private void addId(Long id, int widthLbl) {
        add(Builder.buildLabel(getGuiCnf().getIdLbl(), FontUtil.BOLD_12,
                new Rectangle(RECT_X, RECT_Y, widthLbl, LHEIGHT)));
        int widthVal = FontUtil.FM_PLAIN_12.stringWidth(id.toString());
        add(Builder.buildLabel(id.toString(), FontUtil.PLAIN_12, new Rectangle(
                widthLbl, RECT_Y, widthVal, LHEIGHT)));
        pnlWidth = pnlWidth + widthLbl + widthVal;
        y = RECT_Y + LHEIGHT;
    }
    
    private void addPoint(LatLon point, int widthLbl) {
        add(Builder.buildLabel(getGuiCnf().getPointLbl(), FontUtil.BOLD_12,
                new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
        String pointStr = Formatter.formatLatLon(point);
        int widthVal = FontUtil.FM_PLAIN_12.stringWidth(pointStr);
        add(Builder.buildLabel(pointStr, FontUtil.PLAIN_12, new Rectangle(
                widthLbl, y, widthVal, LHEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LHEIGHT;
    }
    
    private void addHeight(Double height, int widthLbl) {
        if (height != null) {
            add(Builder.buildLabel(getGuiCnf().getHeightLbl(), FontUtil.BOLD_12, 
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            String heightStr = Formatter.formatDecimal(height, DecFormat.SHORT) 
                    + " m";
            int widthVal = FontUtil.FM_PLAIN_12.stringWidth(heightStr);
            add(Builder.buildLabel(heightStr, FontUtil.PLAIN_12, new Rectangle(
                    widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }
    
    private void addType(String type, int widthLbl) {
        add(Builder.buildLabel(getGuiCnf().getTypeLbl(), FontUtil.BOLD_12,
                new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
        int widthVal = FontUtil.FM_PLAIN_12.stringWidth(type);
        Icon icon = TypeIconFactory.getInstance().getIcon(type, false);
        add(Builder.buildLabel(icon, new Rectangle(widthLbl, y, widthVal,
                LHEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LHEIGHT;
    }
    
    private void addStatus(Status status, int widthLbl) {
        add(Builder.buildLabel(getGuiCnf().getStatusLbl(), FontUtil.BOLD_12,
                new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
        int widthVal = FontUtil.FM_PLAIN_12.stringWidth(status.name());
        add(Builder.buildLabel(status.name(), FontUtil.PLAIN_12, new Rectangle(
                widthLbl, y, widthVal, LHEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LHEIGHT;
    }
    
    private void addCreated(Long created, int widthLbl) {
        add(Builder.buildLabel(getGuiCnf().getCreatedLbl(), FontUtil.BOLD_12,
                new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
        String createdStr = Formatter.formatTimestamp(created);
        int widthVal = FontUtil.FM_PLAIN_12.stringWidth(createdStr);
        add(Builder.buildLabel(createdStr, FontUtil.PLAIN_12, new Rectangle(
                widthLbl, y, widthVal, LHEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LHEIGHT;
    }
    
    private void addDupl(Long duplicateOf, int widthLbl) {
        if (duplicateOf != null) {
            add(Builder.buildLabel(getGuiCnf().getDuplLbl(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            int widthVal = FontUtil.FM_PLAIN_12.stringWidth(duplicateOf.toString());
            add(Builder.buildLabel(duplicateOf.toString(), FontUtil.PLAIN_12,
                    new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }
}