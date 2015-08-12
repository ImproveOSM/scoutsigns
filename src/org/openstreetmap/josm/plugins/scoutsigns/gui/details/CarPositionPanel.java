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
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.Dimension;
import java.awt.Rectangle;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.CarPosition;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;


/**
 * Builds a panel for displaying the properties of a {@code CarPosition}.
 *
 * @author Bea
 * @version $Revision$
 */
class CarPositionPanel extends InfoPanel<CarPosition> {

    private static final long serialVersionUID = -1633702812150337414L;

    private static final int LIMIT = 180;

    private int y = 0;
    private int pnlWidth = 0;


    private void addAccuracy(final Integer accuracy, final int widthLbl) {
        if (accuracy != null) {
            add(Builder.buildLabel(getGuiCnf().getLblAcc(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final String accStr = accuracy.toString();
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(accStr);
            add(Builder.buildLabel(accStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addDirection(final Integer heading, final int widthLbl) {
        add(Builder.buildLabel(getGuiCnf().getLblDirection(), FontUtil.BOLD_12,
                new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
        final String direction = heading < LIMIT ? getGuiCnf().getLblForward() : getGuiCnf().getLblBackward();
        final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(direction);
        add(Builder.buildLabel(direction, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LHEIGHT;
    }

    private void addHeading(final Integer heading, final int widthLbl) {
        add(Builder.buildLabel(getGuiCnf().getLblHeading(), FontUtil.BOLD_12,
                new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
        final String headingStr = heading.toString();
        final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(headingStr);
        add(Builder.buildLabel(headingStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LHEIGHT;
    }

    private void addPoint(final LatLon point, final int widthLbl) {
        if (point != null) {
            add(Builder.buildLabel(getGuiCnf().getLblPoint(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, RECT_Y, widthLbl, LHEIGHT)));
            final String pointStr = Formatter.formatLatLon(point);
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(pointStr);
            add(Builder.buildLabel(pointStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, RECT_Y, widthVal, LHEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = RECT_Y + LHEIGHT;
        }
    }

    private void addType(final String type, final int widthLbl) {
        if (type != null) {
            add(Builder.buildLabel(getGuiCnf().getLblType(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(type);
            add(Builder.buildLabel(type, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    @Override
    void createComponents(final CarPosition obj) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(FontUtil.FM_BOLD_12, getGuiCnf().getLblPoint(), getGuiCnf().getLblType(),
                getGuiCnf().getLblHeading(), getGuiCnf().getLblDirection(), getGuiCnf().getLblAcc());
        addPoint(obj.getPosition(), widthLbl);
        addType(obj.getType(), widthLbl);
        if (obj.getHeading() != null) {
            addHeading(obj.getHeading(), widthLbl);
            addDirection(obj.getHeading(), widthLbl);
        }
        addAccuracy(obj.getAccuracy(), widthLbl);
        final int pnlHeight = y + SPACE_Y;
        setPreferredSize(new Dimension(pnlWidth + SPACE_Y, pnlHeight));
    }
}