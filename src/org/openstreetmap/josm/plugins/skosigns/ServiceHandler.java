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
package org.openstreetmap.josm.plugins.skosigns;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.plugins.skosigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.skosigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.skosigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.skosigns.service.FcdSignService;
import org.openstreetmap.josm.plugins.skosigns.service.FcdSignServiceException;
import org.openstreetmap.josm.plugins.skosigns.util.pref.PrefManager;


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
    
    private void handleException(Exception ex, boolean suppress) {
        if (suppress) {
            if (!PrefManager.getInstance().loadSupressErrorFlag()) {
                PrefManager.getInstance().saveSupressErrorFlag(suppress);
                JOptionPane.showMessageDialog(Main.parent, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(Main.parent, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}