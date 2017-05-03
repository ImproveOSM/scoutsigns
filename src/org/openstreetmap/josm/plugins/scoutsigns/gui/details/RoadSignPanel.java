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
import org.openstreetmap.josm.plugins.scoutsigns.gui.Formatter;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import com.telenav.josm.common.formatter.DateFormatter;
import com.telenav.josm.common.formatter.DecimalPattern;
import com.telenav.josm.common.formatter.EntityFormatter;
import com.telenav.josm.common.gui.BasicInfoPanel;
import com.telenav.josm.common.gui.builder.LabelBuilder;


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


    @Override
    protected void createComponents(final RoadSign roadSign) {
        y = 0;
        pnlWidth = 0;
        final int widthLbl = getMaxWidth(getFontMetrics(getFont().deriveFont(Font.BOLD)), GUI_CONF.getLblId(),
                GUI_CONF.getLblPoint(), GUI_CONF.getLblType(), GUI_CONF.getLblStatus(), GUI_CONF.getLblConf(),
                GUI_CONF.getLblCreated(), GUI_CONF.getLblDupl());
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
            add(LabelBuilder.build(GUI_CONF.getLblConf(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));

            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(confidence.toString());
            add(LabelBuilder.build(confidence.toString(), Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addCreated(final Long created, final int widthLbl) {
        if (created != null) {
            add(LabelBuilder.build(GUI_CONF.getLblCreated(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String createdStr = DateFormatter.formatTimestamp(created);
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(createdStr);
            add(LabelBuilder.build(createdStr, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addDupl(final Long duplicateOf, final int widthLbl) {
        if (duplicateOf != null) {
            add(LabelBuilder.build(GUI_CONF.getLblDupl(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(duplicateOf.toString());
            add(LabelBuilder.build(duplicateOf.toString(), Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addHeight(final Double height, final int widthLbl) {
        if (height != null) {
            add(LabelBuilder.build(GUI_CONF.getLblHeight(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String heightStr = EntityFormatter.formatDouble(height, false, DecimalPattern.SHORT) + " m";
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(heightStr);
            add(LabelBuilder.build(heightStr, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addId(final Long id, final int widthLbl) {
        if (id != null) {
            add(LabelBuilder.build(GUI_CONF.getLblId(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(id.toString());
            add(LabelBuilder.build(id.toString(), Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = pnlWidth + widthLbl + widthVal;
            y = y + LINE_HEIGHT;
        }
    }

    private void addPoint(final LatLon point, final int widthLbl) {
        if (point != null) {
            add(LabelBuilder.build(GUI_CONF.getLblPoint(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final String pointStr = Formatter.formatLatLon(point);
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(pointStr);
            add(LabelBuilder.build(pointStr, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addStatus(final Status status, final int widthLbl) {
        if (status != null) {
            add(LabelBuilder.build(GUI_CONF.getLblStatus(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(status.name());
            add(LabelBuilder.build(status.name(), Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));

            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }

    private void addType(final String type, final int widthLbl) {
        if (type != null) {
            add(LabelBuilder.build(GUI_CONF.getLblType(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                    SwingConstants.LEFT, SwingConstants.TOP, new Rectangle(RECT_X, y, widthLbl, LINE_HEIGHT)));
            final int widthVal = getFontMetrics(getFont().deriveFont(Font.PLAIN)).stringWidth(type);
            add(LabelBuilder.build(type, Font.PLAIN, ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT,
                    SwingConstants.TOP, new Rectangle(widthLbl, y, widthVal, LINE_HEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LINE_HEIGHT;
        }
    }
}