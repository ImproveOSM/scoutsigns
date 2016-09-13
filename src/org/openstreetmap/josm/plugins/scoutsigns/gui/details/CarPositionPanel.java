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
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.CarPosition;
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


    @Override
    protected void createComponents(final CarPosition obj) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(Main.map.mapView.getGraphics().getFontMetrics(getFontBold()),
                GUI_CONF.getLblPoint(), GUI_CONF.getLblType(), GUI_CONF.getLblHeading(), GUI_CONF.getLblDirection(),
                GUI_CONF.getLblAcc());
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

    private void addAccuracy(final Integer accuracy, final int widthLbl) {
        if (accuracy != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblAcc(), getFontBold(), ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String accStr = accuracy.toString();
            final int widthVal = Main.map.mapView.getGraphics().getFontMetrics(getFontPlain()).stringWidth(accStr);
            add(GuiBuilder.buildLabel(accStr, getFontPlain(), ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addDirection(final Integer heading, final int widthLbl) {
        add(GuiBuilder.buildLabel(GUI_CONF.getLblDirection(), getFontBold(), ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
        final String direction = heading < LIMIT ? GUI_CONF.getLblForward() : GUI_CONF.getLblBackward();
        final int widthVal = Main.map.mapView.getGraphics().getFontMetrics(getFontPlain()).stringWidth(direction);
        add(GuiBuilder.buildLabel(direction, getFontPlain(), ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LINE_HEIGHT;
    }

    private void addHeading(final Integer heading, final int widthLbl) {
        add(GuiBuilder.buildLabel(GUI_CONF.getLblHeading(), getFontBold(), ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
        final String headingStr = heading.toString();
        final int widthVal = Main.map.mapView.getGraphics().getFontMetrics(getFontPlain()).stringWidth(headingStr);
        add(GuiBuilder.buildLabel(headingStr, getFontPlain(), ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
        pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
        y = y + LINE_HEIGHT;
    }

    private void addPoint(final LatLon point, final int widthLbl) {
        if (point != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblPoint(), getFontBold(), ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String pointStr = Formatter.formatLatLon(point);
            final int widthVal = Main.map.mapView.getGraphics().getFontMetrics(getFontPlain()).stringWidth(pointStr);
            add(GuiBuilder.buildLabel(pointStr, getFontPlain(), ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = y + LINE_HEIGHT;
        }
    }

    private void addType(final String type, final int widthLbl) {
        if (type != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblType(), getFontBold(), ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = Main.map.mapView.getGraphics().getFontMetrics(getFontPlain()).stringWidth(type);
            add(GuiBuilder.buildLabel(type, getFontPlain(), ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }
}