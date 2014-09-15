/*
 * Copyright =c) 2014 SKOBBLER SRL.
 * Cuza Voda 1, Cluj-Napoca, Cluj, 400107, Romania
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL 
 * ="Confidential Information"). You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the license 
 * agreement you entered into with SKOBBLER SRL.
 * 
 * Created on Jul 29, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.service;


/**
 * Defines the FcdSignService method and parameter names.
 * 
 * @author Bea
 * @version $Revision$
 */
final class Constants {
    
    private Constants() {}
    
    /* request parameters used by any operation */
    static final String FORMAT = "format";
    static final String FORMAT_VAL = "json";
    
    /* "searchSigns" method & parameters */
    static final String SEARCH_SIGNS = "searchSigns";
    static final String NORTH = "north";
    static final String SOUTH = "south";
    static final String EAST = "east";
    static final String WEST = "west";
    static final String ZOOM = "zoom";
    static final String FROM = "from";
    static final String TO = "to";
    static final String TYPE = "type";
    static final String STATUS = "status";
    static final String DUPLICATE_OF = "duplicateOf";
    static final String APPNAME = "appName";
    static final String APPVER = "appVer";
    static final String OSNAME = "osName";
    static final String OSVER = "osVer";
    static final String USERNAME = "username";
    
    /* "retrieveSign" method & parameter */
    static final String RETRIEVE_SIGN = "retrieveSign";
    static final String ID = "id";
}