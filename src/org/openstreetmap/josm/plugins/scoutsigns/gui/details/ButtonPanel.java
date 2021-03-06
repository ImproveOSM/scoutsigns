/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter.RoadSignFilterDialog;
import org.openstreetmap.josm.plugins.scoutsigns.observer.StatusChangeObserver;
import org.openstreetmap.josm.plugins.scoutsigns.observer.TripViewObservable;
import org.openstreetmap.josm.plugins.scoutsigns.observer.TripViewObserver;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TltConfig;
import org.openstreetmap.josm.tools.GuiSizesHelper;
import com.telenav.josm.common.gui.builder.ButtonBuilder;


/**
 * Defines the button panel for the road sign details dialog.
 *
 * @author Bea
 * @version $Revision$
 */
class ButtonPanel extends JPanel implements TripViewObservable {


    private static final long serialVersionUID = -853684446082269916L;

    private static final Dimension DIM = new Dimension(200, 23);
    private static final int ROWS = 1;
    private static final int COLS = 5;

    private TripViewObserver tripViewObserver;
    private StatusChangeObserver statusChangeObserver;

    /* the selected road sign */
    private RoadSign roadSign;

    /* UI components */
    private final JButton btnFilter;
    private final JButton btnImage;
    private final JButton btnComment;
    private final JButton btnMoreAction;
    private final JButton btnBack;
    private final JButton btnTrip;
    private ImageFrame imgFrame;
    private boolean tripView = false;

    /* the list of statuses that a road sign/ set of road signs might have */
    private List<Status> statuses = new ArrayList<>(Status.VALUES_LIST);


    /**
     * Builds a new {@code ButtonPanel}
     */
    ButtonPanel() {
        super(new GridLayout(ROWS, COLS));

        // create components
        final IconConfig iconCnf = IconConfig.getInstance();
        final TltConfig tltCnf = TltConfig.getInstance();
        btnFilter =
                ButtonBuilder.build(new DisplayFilterDialog(), iconCnf.getFilterIcon(), tltCnf.getBtnFilter(), false);
        btnBack = ButtonBuilder.build(new ExitTrip(), iconCnf.getBackIcon(), tltCnf.getBtnBack(), true);
        btnTrip = ButtonBuilder.build(new DisplayTrip(), iconCnf.getTripIcon(), tltCnf.getBtnTrip(), true);
        btnImage = ButtonBuilder.build(new DisplayImageFrame(), iconCnf.getPhotoIcon(), tltCnf.getBtnPhoto(), true);
        btnComment =
                ButtonBuilder.build(new DisplayCommentDialog(), iconCnf.getCommentIcon(), tltCnf.getBtnComment(), true);
        btnMoreAction = ButtonBuilder.build(new DisplayEditMenu(), iconCnf.getMoreActionIcon(),
                tltCnf.getBtnMoreAction(), true);

        // disable actions
        enableRoadSignActions();

        // add components
        add(btnFilter);
        add(btnImage);
        add(btnTrip);
        add(btnComment);
        add(btnMoreAction);
        setPreferredSize(GuiSizesHelper.getDimensionDpiAdjusted(DIM));
    }


    /**
     * Enables or disabled action buttons based on the given zoom level and trip view.
     *
     * @param zoom the current zoom level.
     */
    void enableButtons(final int zoom, final boolean isTripView) {
        if (zoom > Config.getInstance().getMaxClusterZoom()) {
            if (!isTripView) {
                setRoadSign(null);
                handleExitTrip();
            }
            enableRoadSignActions();
            btnFilter.setEnabled(true);
        } else {
            btnFilter.setEnabled(false);
            btnImage.setEnabled(false);
            btnTrip.setEnabled(false);
            btnComment.setEnabled(false);
            btnMoreAction.setEnabled(false);
        }
    }

    private void handleExitTrip() {
        remove(0);
        add(btnFilter, 0);
        tripView = false;
    }

    /**
     * Sets the selected road sign.
     *
     * @param roadSign a {@code RoadSign}
     */
    void setRoadSign(final RoadSign roadSign) {
        this.roadSign = roadSign;

        // restore possible statuses & enable/disable selected road sign related actions
        if (statuses.size() != Status.VALUES_LIST.size()) {
            statuses = new ArrayList<>(Status.VALUES_LIST);
        }
        if (this.roadSign != null) {
            statuses.remove(this.roadSign.getStatus());

            // reload image
            if (imgFrame != null && imgFrame.isVisible()) {
                imgFrame.update(roadSign);
                imgFrame.repaint();
            }
        } else if (imgFrame != null && imgFrame.isVisible()) {
            imgFrame.dispose();
        }
        enableRoadSignActions();
    }

    private void enableRoadSignActions() {
        boolean enableActions = false;
        if (roadSign != null) {
            enableActions = true;
        }
        btnImage.setEnabled(enableActions);
        if (tripView) {
            btnTrip.setEnabled(false);
        } else {
            btnTrip.setEnabled(enableActions);
        }
        btnComment.setEnabled(enableActions);
        btnMoreAction.setEnabled(enableActions);
    }

    @Override
    public void notifyObserver(final boolean enterView) {
        if (enterView) {
            this.tripViewObserver.enterTripView();
        } else {
            this.tripViewObserver.exitTripView();
        }
    }

    @Override
    public void registerObserver(final TripViewObserver tripViewObserver) {
        this.tripViewObserver = tripViewObserver;
    }

    /**
     * Registers the given observer.
     *
     * @param observer a {@code StatusChangeObserver}
     */
    void registerStatusChangeObserver(final StatusChangeObserver observer) {
        statusChangeObserver = observer;
    }


    /*
     * Displays the comment dialog window.
     */
    private final class DisplayCommentDialog extends AbstractAction {

        private static final long serialVersionUID = -2470311157850355646L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            final EditDialog dlgComment = new EditDialog(null, GuiConfig.getInstance().getDlgCommentTitle(),
                    IconConfig.getInstance().getCommentIcon().getImage());
            dlgComment.registerObserver(statusChangeObserver);
            dlgComment.setVisible(true);
        }
    }

    /*
     * Displays the edit menu.
     */
    private final class DisplayEditMenu extends AbstractAction {

        private static final long serialVersionUID = 5945560671001154104L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            final EditPopupMenu editMenu = new EditPopupMenu(statuses);
            editMenu.registerStatusChangeObserver(statusChangeObserver);
            editMenu.show(ButtonPanel.this, 0, 0);
            final Point point = getComponent(getComponentCount() - 1).getLocationOnScreen();
            editMenu.setLocation(point.x, point.y - getHeight());
        }
    }

    /*
     * Displays the filter dialog window. This dialog window is available only when road signs are displayed on the map.
     */
    private final class DisplayFilterDialog extends AbstractAction {

        private static final long serialVersionUID = -7084091586699723933L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            final RoadSignFilterDialog dlgFilter = new RoadSignFilterDialog();
            dlgFilter.setVisible(true);
        }
    }

    /*
     * Displays the selected road sign's image. If the frame is already opened updates it's content.
     */
    private final class DisplayImageFrame extends AbstractAction {

        private static final long serialVersionUID = 5500399753585606903L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (imgFrame != null && imgFrame.isVisible()) {
                imgFrame.update(roadSign);
                imgFrame.repaint();
            } else {
                imgFrame = new ImageFrame(roadSign);
                imgFrame.pack();
                imgFrame.setVisible(true);
            }
        }
    }

    /*
     * Displays the selected road sign's trip.
     */
    private final class DisplayTrip extends AbstractAction {

        private static final long serialVersionUID = 559317768633883689L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            if (roadSign.getNearbyPos() != null) {
                remove(0);
                add(btnBack, 0);
                btnTrip.setEnabled(false);
                tripView = true;
                revalidate();
                repaint();
                notifyObserver(true);
            }
        }
    }

    /*
     * Exit the trip view.
     */
    private final class ExitTrip extends AbstractAction {

        private static final long serialVersionUID = -5015385030138059426L;

        @Override
        public void actionPerformed(final ActionEvent event) {
            handleExitTrip();
            btnTrip.setEnabled(true);
            repaint();
            notifyObserver(false);
        }
    }
}