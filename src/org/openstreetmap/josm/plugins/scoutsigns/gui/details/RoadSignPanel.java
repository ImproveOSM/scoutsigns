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
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;
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
    void createComponents(final RoadSign roadSign) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl =
                getMaxWidth(FontUtil.FM_BOLD_12, getGuiCnf().getLblId(), getGuiCnf().getLblPoint(), getGuiCnf()
                        .getLblType(), getGuiCnf().getLblStatus(), getGuiCnf().getLblConf(), getGuiCnf()
                        .getLblCreated(), getGuiCnf().getLblDupl());
        addId(roadSign.getId(), widthLbl);
        addPoint(roadSign.getSignPos().getPosition(), widthLbl);
        addHeight(roadSign.getSignPos().getHeight(), widthLbl);
        addType(roadSign.getType(), widthLbl);
        addStatus(roadSign.getStatus(), widthLbl);
        addConfidence(roadSign.getConfidence(), widthLbl);
        addCreated(roadSign.getTstamp(), widthLbl);
        addDupl(roadSign.getDuplicateOf(), widthLbl);
        final int pnlHeight = y + SPACE_Y;
        setPreferredSize(new Dimension(pnlWidth + SPACE_Y, pnlHeight));
    }

    private void addConfidence(final Short confidence, final int widthLbl) {
        if (confidence != null) {
            add(Builder.buildLabel(getGuiCnf().getLblConf(), FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                    LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(confidence.toString());
            add(Builder.buildLabel(confidence.toString(), FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal,
                    LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addCreated(final Long created, final int widthLbl) {
        if (created != null) {
            add(Builder.buildLabel(getGuiCnf().getLblCreated(), FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                    LHEIGHT)));
            final String createdStr = DateUtil.formatTimestamp(created);
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(createdStr);
            add(Builder.buildLabel(createdStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addDupl(final Long duplicateOf, final int widthLbl) {
        if (duplicateOf != null) {
            add(Builder.buildLabel(getGuiCnf().getLblDupl(), FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                    LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(duplicateOf.toString());
            add(Builder.buildLabel(duplicateOf.toString(), FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal,
                    LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addHeight(final Double height, final int widthLbl) {
        if (height != null) {
            add(Builder.buildLabel(getGuiCnf().getLblHeight(), FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                    LHEIGHT)));
            final String heightStr = Formatter.formatDecimal(height, DecFormat.SHORT) + " m";
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(heightStr);
            add(Builder.buildLabel(heightStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addId(final Long id, final int widthLbl) {
        if (id != null) {
            add(Builder.buildLabel(getGuiCnf().getLblId(), FontUtil.BOLD_12, new Rectangle(RECT_X, RECT_Y, widthLbl,
                    LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(id.toString());
            add(Builder
                    .buildLabel(id.toString(), FontUtil.PLAIN_12, new Rectangle(widthLbl, RECT_Y, widthVal, LHEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = RECT_Y + LHEIGHT;
        }
    }

    private void addPoint(final LatLon point, final int widthLbl) {
        if (point != null) {
            add(Builder.buildLabel(getGuiCnf().getLblPoint(), FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                    LHEIGHT)));
            final String pointStr = Formatter.formatLatLon(point);
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(pointStr);
            add(Builder.buildLabel(pointStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addStatus(final Status status, final int widthLbl) {
        if (status != null) {
            add(Builder.buildLabel(getGuiCnf().getLblStatus(), FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                    LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(status.name());
            add(Builder.buildLabel(status.name(), FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addType(final String type, final int widthLbl) {
        if (type != null) {
            add(Builder.buildLabel(getGuiCnf().getLblType(), FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                    LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(type);
            add(Builder.buildLabel(type, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }
}