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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Trip;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import com.telenav.josm.common.gui.GuiBuilder;


/**
 * Builds a panel for displaying the details of a {@code Trip}.
 *
 * @author Bea
 * @version $Revision$
 */
class TripPanel extends InfoPanel<Trip> {

    private static final long serialVersionUID = 3055968309458580598L;
    private static final int ID_HEIGHT = 35;
    private static final int ID_WIDTH = 210;
    private int y = 0;
    private int pnlWidth = 0;


    private void addApp(final Application app, final int widthLbl) {
        if (app != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblApp(), FontUtil.BOLD_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String appStr = app.toString();
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(appStr);
            add(GuiBuilder.buildLabel(appStr, FontUtil.PLAIN_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addDevice(final Device device, final int widthLbl) {
        if (device != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblDevice(), FontUtil.BOLD_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String deviceStr = device.toString();
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(deviceStr);
            add(GuiBuilder.buildLabel(deviceStr, FontUtil.PLAIN_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addId(final String id, final int widthLbl) {
        if (id != null) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblId(), FontUtil.BOLD_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, RECT_Y, widthLbl, LINE_HEIGHT)));
            final JTextArea txtArea = GuiBuilder.buildTextArea(id, getBackground(), false, FontUtil.PLAIN_12);
            txtArea.setBounds(new Rectangle(widthLbl, RECT_Y, ID_WIDTH, ID_HEIGHT));
            add(txtArea);
            pnlWidth = pnlWidth + widthLbl + ID_WIDTH;
            y = RECT_Y + ID_HEIGHT;
        }
    }

    private void addMode(final String mode, final int widthLbl) {
        if (mode != null && !mode.isEmpty()) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblMode(), FontUtil.BOLD_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(mode);
            add(GuiBuilder.buildLabel(mode, FontUtil.PLAIN_12, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addProfile(final String profile, final int widthLbl) {
        if (profile != null && !profile.isEmpty()) {
            add(GuiBuilder.buildLabel(getGuiCnf().getLblProfile(), FontUtil.BOLD_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(profile);
            add(GuiBuilder.buildLabel(profile, FontUtil.PLAIN_12, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    @Override
    protected void createComponents(final Trip trip) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(FontUtil.FM_BOLD_12, getGuiCnf().getLblId(), getGuiCnf().getLblMode(),
                getGuiCnf().getLblProfile(), getGuiCnf().getLblApp(), getGuiCnf().getLblDevice());
        addId(trip.getId(), widthLbl);
        addMode(trip.getMode(), widthLbl);
        addProfile(trip.getProfile(), widthLbl);
        addApp(trip.getApp(), widthLbl);
        addDevice(trip.getDevice(), widthLbl);
        final int pnlHeight = y + SPACE_Y;
        setPreferredSize(new Dimension(pnlWidth + SPACE_Y, pnlHeight));
    }
}