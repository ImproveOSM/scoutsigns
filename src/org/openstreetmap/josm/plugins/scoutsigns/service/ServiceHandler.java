/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.service;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.DataSet;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSignCluster;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import com.telenav.josm.common.argument.BoundingBox;


/**
 * Executes the service operations corresponding to the user's actions.
 *
 * @author Bea
 * @version $Revision$
 */
public final class ServiceHandler {

    private static final ServiceHandler UNIQUE_INSTANCE = new ServiceHandler();

    /**
     * Returns the unique instance of the {@code ServiceHandler} object.
     *
     * @return a {@code ServiceHandler}
     */
    public static ServiceHandler getInstance() {
        return UNIQUE_INSTANCE;
    }

    private final FcdSignService signService = new FcdSignService();


    /**
     * Adds a comment to the given road sign. If the status is not null, then also the road sign's status is modified.
     *
     * @param signId the road sign's identifier
     * @param username the user's OSM username
     * @param text the comment text
     * @param status the road sign's new {@code Status}
     * @param duplicateOf specifies the parent road sign's identifier, it is user only with {@code Status#DUPLICATE}
     */
    public void addComment(final Long signId, final String username, final String text, final Status status,
            final Long duplicateOf) {
        try {
            signService.addComment(signId, username, text, status, duplicateOf);
        } catch (final FcdSignServiceException ex) {
            handleException(ex, false);
        }
    }

    /**
     * Adds the same comment to every road sign from the given collection. If the status is not null, then also the
     * status of the road signs are modified. This is a batch operation equivalent to calling "addComment" on each
     * individual road sign from the collection.
     *
     * @param roadSigns the collection of selected road signs
     * @param username the user's OSM username
     * @param text the comment text
     * @param status the new {@code Status} to be set
     * @param duplicateOf specifies the parent road sign's identifier, it is user only with {@code Status#DUPLICATE}
     */
    public void addComments(final List<RoadSign> roadSigns, final String username, final String text,
            final Status status, final Long duplicateOf) {
        final List<Long> signIds = new ArrayList<>();
        for (final RoadSign roadSign : roadSigns) {
            signIds.add(roadSign.getId());
        }
        try {
            signService.addComments(signIds, username, text, status, duplicateOf);
        } catch (final FcdSignServiceException ex) {
            handleException(ex, false);
        }
    }

    /**
     * Retrieves the road sign corresponding to the given identifier.
     *
     * @param id the identifier of the desired road sign
     * @return a {@code RoadSign} object
     */
    public RoadSign retrieveSign(final Long id) {
        RoadSign result = null;
        try {
            result = signService.retrieveRoadSign(id);
        } catch (final FcdSignServiceException ex) {
            handleException(ex, false);
        }
        return result;
    }

    /**
     * Depending on the zoom levels either:
     * <ul>
     * <li>searches for the road sign clusters from the current bounding box</li>
     * <li>searches for the road signs from the current bounding box, that satisfy the given filters</li>
     * </ul>
     *
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param filter specifies the search filters
     * @param zoom the current zoom level
     * @return a {@code DataSet} representing the road signs/road sign clusters from the given bounding box
     */
    public DataSet search(final BoundingBox bbox, final SearchFilter filter, final int zoom) {
        List<RoadSign> roadSigns = null;
        List<RoadSignCluster> roadSignClusters = null;
        try {
            if (zoom > Config.getInstance().getMaxClusterZoom()) {
                roadSigns = signService.searchSigns(bbox, filter, zoom);
            } else {
                roadSignClusters = signService.searchClusters(bbox, zoom);
            }
        } catch (final Exception ex) {
            handleException(ex, true);
        }
        return new DataSet(roadSigns, roadSignClusters);
    }

    private void handleException(final Exception ex, final boolean suppress) {
        if (suppress) {
            if (!PrefManager.getInstance().loadSupressErrorFlag()) {
                PrefManager.getInstance().saveSupressErrorFlag(suppress);
                JOptionPane.showMessageDialog(MainApplication.getMainFrame(), ex.getMessage(), "Operation failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(MainApplication.getMainFrame(), ex.getMessage(), "Operation failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}