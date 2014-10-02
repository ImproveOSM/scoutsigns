/*
 * Copyright (c) 2014 SKOBBLER SRL.
 * Cuza Voda 1, Cluj-Napoca, Cluj, 400107, Romania
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL 
 * ("Confidential Information"). You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the license 
 * agreement you entered into with SKOBBLER SRL.
 * 
 * Created on Jul 29, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.scoutsigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.service.FcdSignService;
import org.openstreetmap.josm.plugins.scoutsigns.service.FcdSignServiceException;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


/**
 * Executes the service operations corresponding to the user's actions.
 * 
 * @author Bea
 * @version $Revision$
 */
final class ServiceHandler {
    
    private FcdSignService signService = new FcdSignService();
    
    private static final ServiceHandler UNIQUE_INSTANCE = new ServiceHandler();
    
    /**
     * Returns the unique instance of the {@code ServiceHandler} object.
     * 
     * @return a {@code ServiceHandler}
     */
    static ServiceHandler getInstance() {
        return UNIQUE_INSTANCE;
    }
    
    /**
     * Searches for the road signs from the given area that satisfy the given
     * filters.
     * 
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param filter specifies the search filters
     * @param zoom the current zoom level
     * @return a collection of {@code RoadSign}. If no road signs are found, the
     * method returns an empty collection.
     */
    Collection<RoadSign> searchSigns(BoundingBox bbox, SearchFilter filter,
            int zoom) {
        Collection<RoadSign> result = new ArrayList<>();
        try {
            result = signService.searchSigns(bbox, filter, zoom);
        } catch (FcdSignServiceException ex) {
            handleException(ex, true);
        }
        return result;
    }
    
    /**
     * Retrieves the road sign corresponding to the given identifier.
     * 
     * @param id the identifier of the desired road sign
     * @return a {@code RoadSign} object
     */
    RoadSign retrieveSign(Long id) {
        RoadSign result = null;
        try {
            result = signService.retrieveRoadSign(id);
        } catch (FcdSignServiceException ex) {
            handleException(ex, false);
        }
        return result;
    }
    
    /**
     * Creates a new comment for the specified road sign with the given
     * arguments.
     * 
     * @param signId the road sign's identifier
     * @param username the user's OSM username
     * @param text the comment text
     * @param status the road sign's new {@code Status}
     * @param duplicateOf it is used only with {@code Status#DUPLICATE}.
     * Specifies the parent road sign's identifier.
     */
    void addComment(Long signId, String username, String text, Status status,
            Long duplicateOf) {
        try {
            signService.addComment(signId, username, text, status, duplicateOf);
        } catch (FcdSignServiceException ex) {
            handleException(ex, false);
        }
    }
    
    
    private void handleException(Exception ex, boolean suppress) {
        if (suppress) {
            if (!PrefManager.getInstance().loadSupressErrorFlag()) {
                PrefManager.getInstance().saveSupressErrorFlag(suppress);
                JOptionPane.showMessageDialog(Main.parent, ex.getMessage(),
                        "Operation failed", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(Main.parent, ex.getMessage(),
                    "Operation failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}