package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.Dimension;
import java.awt.Rectangle;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Trip;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;


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
            add(Builder.buildLabel(getGuiCnf().getLblApp(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final String appStr = app.toString();
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(appStr);
            add(Builder.buildLabel(appStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addDevice(final Device device, final int widthLbl) {
        if (device != null) {
            add(Builder.buildLabel(getGuiCnf().getLblDevice(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final String deviceStr = device.toString();
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(deviceStr);
            add(Builder.buildLabel(deviceStr, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addId(final String id, final int widthLbl) {
        if (id != null) {
            add(Builder.buildLabel(getGuiCnf().getLblId(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, RECT_Y, widthLbl, LHEIGHT)));
            add(Builder.buildTextArea(id, false, FontUtil.PLAIN_12, getBackground(),
                    new Rectangle(widthLbl, RECT_Y, ID_WIDTH, ID_HEIGHT)));
            pnlWidth = pnlWidth + widthLbl + ID_WIDTH;
            y = RECT_Y + ID_HEIGHT;
        }
    }

    private void addMode(final String mode, final int widthLbl) {
        if (mode != null && !mode.isEmpty()) {
            add(Builder.buildLabel(getGuiCnf().getLblMode(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(mode);
            add(Builder.buildLabel(mode, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    private void addProfile(final String profile, final int widthLbl) {
        if (profile != null && !profile.isEmpty()) {
            add(Builder.buildLabel(getGuiCnf().getLblProfile(), FontUtil.BOLD_12,
                    new Rectangle(RECT_X, y, widthLbl, LHEIGHT)));
            final int widthVal = FontUtil.FM_PLAIN_12.stringWidth(profile);
            add(Builder.buildLabel(profile, FontUtil.PLAIN_12, new Rectangle(widthLbl, y, widthVal, LHEIGHT)));
            pnlWidth = Math.max(pnlWidth, widthLbl + widthVal);
            y = y + LHEIGHT;
        }
    }

    @Override
    void createComponents(final Trip trip) {
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