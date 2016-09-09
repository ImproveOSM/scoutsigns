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

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.CarPosition;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import com.telenav.josm.common.gui.BasicInfoPanel;
import com.telenav.josm.common.gui.GuiBuilder;


/**
 * Builds a panel for displaying the properties of a {@code CarPosition}.
 *
 * @author Bea
 * @version $Revision$
 */
class CarPositionPanel extends BasicInfoPanel<CarPosition> {

    private static final long serialVersionUID = -1633702812150337414L;

    private static final int LIMIT = 180;
    private static final GuiConfig GUI_CONF = GuiConfig.getInstance();

    private int y = 0;
    private int pnlWidth = 0;

    CarPositionPanel() {
        super();
        updateData(null);
    }


    private void addAccuracy(final Integer accuracy, final int widthLbl) {
        if (accuracy != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblAcc(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String accStr = accuracy.toString();
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(accStr);
            add(GuiBuilder.buildLabel(accStr, getFont().deriveFont(Font.PLAIN, 12), ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addDirection(final Integer heading, final int widthLbl) {
        add(GuiBuilder.buildLabel(GUI_CONF.getLblDirection(), getFont().deriveFont(Font.BOLD, 12),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
        final String direction = heading < LIMIT ? GUI_CONF.getLblForward() : GUI_CONF.getLblBackward();
        final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(direction);
        add(GuiBuilder.buildLabel(direction, getFont().deriveFont(Font.PLAIN, 12), ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LINE_HEIGHT;
    }

    private void addHeading(final Integer heading, final int widthLbl) {
        add(GuiBuilder.buildLabel(GUI_CONF.getLblHeading(), getFont().deriveFont(Font.BOLD, 12),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
        final String headingStr = heading.toString();
        final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(headingStr);
        add(GuiBuilder.buildLabel(headingStr, getFont().deriveFont(Font.PLAIN, 12), ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LINE_HEIGHT;
    }

    private void addPoint(final LatLon point, final int widthLbl) {
        if (point != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblPoint(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String pointStr = Formatter.formatLatLon(point);
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(pointStr);
            add(GuiBuilder.buildLabel(pointStr, getFont().deriveFont(Font.PLAIN, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = y + LINE_HEIGHT;
        }
    }

    private void addType(final String type, final int widthLbl) {
        if (type != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblType(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(type);
            add(GuiBuilder.buildLabel(type, getFont().deriveFont(Font.PLAIN, 12), ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    @Override
    protected void createComponents(final CarPosition obj) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(FontUtil.FM_BOLD_12, GUI_CONF.getLblPoint(), GUI_CONF.getLblType(),
                GUI_CONF.getLblHeading(), GUI_CONF.getLblDirection(), GUI_CONF.getLblAcc());
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