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
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Trip;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import com.telenav.josm.common.gui.BasicInfoPanel;
import com.telenav.josm.common.gui.GuiBuilder;


/**
 * Builds a panel for displaying the details of a {@code Trip}.
 *
 * @author Bea
 * @version $Revision$
 */
class TripPanel extends BasicInfoPanel<Trip> {

    private static final long serialVersionUID = 3055968309458580598L;
    private static final GuiConfig GUI_CONF = GuiConfig.getInstance();
    private static final int ID_HEIGHT = 35;
    private static final int ID_WIDTH = 210;
    private int y = 0;
    private int pnlWidth = 0;


    TripPanel() {
        super();
        updateData(null);
    }


    @Override
    protected void createComponents(final Trip trip) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(getFontMetrics(getFont().deriveFont(Font.BOLD)), GUI_CONF.getLblId(),
                GUI_CONF.getLblMode(), GUI_CONF.getLblProfile(), GUI_CONF.getLblApp(), GUI_CONF.getLblDevice());
        addId(trip.getId(), widthLbl);
        addMode(trip.getMode(), widthLbl);
        addProfile(trip.getProfile(), widthLbl);
        addApp(trip.getApp(), widthLbl);
        addDevice(trip.getDevice(), widthLbl);
        final int pnlHeight = y + SPACE_Y;
        setPreferredSize(new Dimension(pnlWidth + SPACE_Y, pnlHeight));
    }

    private void addApp(final Application app, final int widthLbl) {
        if (app != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblApp(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String appStr = app.toString();
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(appStr);
            add(GuiBuilder.buildLabel(appStr, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addDevice(final Device device, final int widthLbl) {
        if (device != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblDevice(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String deviceStr = device.toString();
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(deviceStr);
            add(GuiBuilder.buildLabel(deviceStr, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addId(final String id, final int widthLbl) {
        if (id != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblId(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            add(GuiBuilder.buildTextArea(id, Font.PLAIN, getBackground(), false,
                    new Rectangle(widthLbl, y, ID_WIDTH, ID_HEIGHT)));
            pnlWidth = pnlWidth + widthLbl + ID_WIDTH;
            y = y + ID_HEIGHT;
        }
    }

    private void addMode(final String mode, final int widthLbl) {
        if (mode != null && !mode.isEmpty()) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblMode(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(mode);
            add(GuiBuilder.buildLabel(mode, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addProfile(final String profile, final int widthLbl) {
        if (profile != null && !profile.isEmpty()) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblProfile(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(profile);
            add(GuiBuilder.buildLabel(profile, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }
}