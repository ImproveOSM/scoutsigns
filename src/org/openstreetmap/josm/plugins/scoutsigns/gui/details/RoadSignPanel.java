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
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter.DecFormat;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import com.telenav.josm.common.gui.BasicInfoPanel;
import com.telenav.josm.common.gui.GuiBuilder;
import com.telenav.josm.common.util.DateUtil;


/**
 * Builds a panel for displaying the details of the selected {@code RoadSign}.
 *
 * @author Bea
 * @version $Revision$
 */
class RoadSignPanel extends BasicInfoPanel<RoadSign> {

    private static final long serialVersionUID = -6001820888166694669L;
    private static final GuiConfig GUI_CONF = GuiConfig.getInstance();
    private int y = 0;
    private int pnlWidth = 0;

    RoadSignPanel() {
        super();
        updateData(null);
    }

    private void addConfidence(final Short confidence, final int widthLbl) {
        if (confidence != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblConf(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));

            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(confidence.toString());
            add(GuiBuilder.buildLabel(confidence.toString(), getFont().deriveFont(Font.PLAIN, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addCreated(final Long created, final int widthLbl) {
        if (created != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblCreated(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String createdStr = DateUtil.formatTimestamp(created);
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(createdStr);
            add(GuiBuilder.buildLabel(createdStr, getFont().deriveFont(Font.PLAIN, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addDupl(final Long duplicateOf, final int widthLbl) {
        if (duplicateOf != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblDupl(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(duplicateOf.toString());
            add(GuiBuilder.buildLabel(duplicateOf.toString(), getFont().deriveFont(Font.PLAIN, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addHeight(final Double height, final int widthLbl) {
        if (height != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblHeight(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String heightStr = Formatter.formatDecimal(height, DecFormat.SHORT) + " m";
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(heightStr);
            add(GuiBuilder.buildLabel(heightStr, getFont().deriveFont(Font.PLAIN, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addId(final Long id, final int widthLbl) {
        if (id != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblId(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(id.toString());
            add(GuiBuilder.buildLabel(id.toString(), getFont().deriveFont(Font.PLAIN, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = y + LINE_HEIGHT;
        }
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
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addStatus(final Status status, final int widthLbl) {
        if (status != null) {
            add(GuiBuilder.buildLabel(GUI_CONF.getLblStatus(), getFont().deriveFont(Font.BOLD, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(status.name());
            add(GuiBuilder.buildLabel(status.name(), getFont().deriveFont(Font.PLAIN, 12),
                    ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP,
                    new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));

            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
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
    protected void createComponents(final RoadSign roadSign) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(FontUtil.FM_BOLD_12, GUI_CONF.getLblId(), GUI_CONF.getLblPoint(),
                GUI_CONF.getLblType(), GUI_CONF.getLblStatus(), GUI_CONF.getLblConf(), GUI_CONF.getLblCreated(),
                GUI_CONF.getLblDupl());
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
}