/*
 * Copyright (c) 2015 SKOBBLER SRL.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with SKOBBLER SRL.
 *
 * Created on Mar 11, 2015 by Beata
 * Modified on $Date$
 *          by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.service.mapillary;


/**
 * Defines the Mapillary API method and parameter names.
 *
 * @author Beata
 * @version $Revision$
 */
final class Constants {

    static final String SEARCH = "search/im/or";

    static final String CLIENT_ID = "client_id";
    static final String START_TIME = "start_time";
    static final String END_TIME = "end_time";
    static final String MIN_LAT = "min_lat";
    static final String MAX_LAT = "max_lat";
    static final String MIN_LON = "min_lon";
    static final String MAX_LON = "max_lon";
    static final String TYPE = "or_classes[]";
    static final String LIMIT = "limit";
    static final String MIN_SCORE = "min_score";

    private Constants() {}
}