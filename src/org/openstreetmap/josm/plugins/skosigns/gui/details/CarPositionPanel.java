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
package org.openstreetmap.josm.plugins.skosigns.gui.details;

import java.awt.Dimension;
import java.awt.Rectangle;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.skosigns.entity.CarPosition;
import org.openstreetmap.josm.plugins.skosigns.gui.Builder;
import org.openstreetmap.josm.plugins.skosigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.skosigns.gui.Formatter;


/**
 * Builds a panel for displaying the properties of a {@code CarPosition}.
 * 
 * @author Bea
 * @version $Revision$
 */
class CarPositionPanel extends InfoPanel<CarPosition> {
    
    private static final long serialVersionUID = -1633702812150337414L;
    private int y = 0;
    private int pnlWidth = 0;
    
    
    @Override
    void createComponents(CarPosition obj) {
        y = 0;
        pnlWidth = 0;
        int widthLbl = getMaxWidth(FontUtil.FM_BOLD_12, getGuiCnf().getPointLbl(),
                getGuiCnf().getTypeLbl(), getGuiCnf().getHeadingLbl(),
                getGuiCnf().getAccLbl());
        addPoint(obj.getPosition(), widthLbl);
        addType(obj.getType(), widthLbl);
        addHeading(obj.getHeading(), widthLbl);
        addAccuracy(obj.getAccuracy(), widthLbl);
        int pnlHeight = y + SPACE_Y;
        setPreferredSize(new Dimension(pnlWidth + SPACE_Y, pnlHeight));
    }
    
    private void addPoint(LatLon point, int widthLbl) {
        if (point != null) {
            add(Builder.buildLabel(getGuiCnf().getPointLbl(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, RECT_Y, widthLbl, LHEIGHT)));
            String pointStr = Formatter.formatLatLon(point);
            int widthVal = FontUtil.FM_PLAIN_12.stringWidth(pointStr);
            add(Builder.buildLabel(pointStr, FontUtil.PLAIN_12, new Rectangle(
                    widthLbl, RECT_Y, widthVal, LHEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = RECT_Y + LHEIGHT;
        }
    }
    
    private void addType(String type, int widthLbl) {
        if (type != null) {
            add(Builder.buildLabel(getGuiCnf().getTypeLbl(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            int widthVal = FontUtil.FM_PLAIN_12.stringWidth(type);
            add(Builder.buildLabel(type, FontUtil.PLAIN_12, new Rectangle(
                    widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }
    
    private void addHeading(Integer heading, int widthLbl) {
        if (heading != null) {
            add(Builder.buildLabel(getGuiCnf().getHeadingLbl(),
                    FontUtil.BOLD_12, new Rectangle(RECT_X, y, widthLbl,
                            LHEIGHT)));
            String headingStr = heading.toString();
            int widthVal = FontUtil.FM_PLAIN_12.stringWidth(headingStr);
            add(Builder.buildLabel(headingStr, FontUtil.PLAIN_12,
                    new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }
    
    private void addAccuracy(Integer accuracy, int widthLbl) {
        if (accuracy != null) {
            add(Builder.buildLabel(getGuiCnf().getAccLbl(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            String accStr = accuracy.toString();
            int widthVal = FontUtil.FM_PLAIN_12.stringWidth(accStr);
            add(Builder.buildLabel(accStr, FontUtil.PLAIN_12, new Rectangle(
                    widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }
}