/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.service;


/**
 * Defines the FcdSignService method and parameter names.
 *
 * @author Bea
 * @version $Revision$
 */
final class Constants {

    /* request parameters used by any operation */
    static final String FORMAT = "format";

    static final String FORMAT_VAL = "json";
    /* "searchSigns" method & parameters */
    static final String SEARCH_SIGNS = "searchSigns";

    static final String NORTH = "north";
    static final String SOUTH = "south";
    static final String EAST = "east";
    static final String WEST = "west";
    static final String SOURCE = "sources";
    static final String ZOOM = "zoom";
    static final String FROM = "from";
    static final String TO = "to";
    static final String TYPES = "types";
    static final String STATUS = "status";
    static final String DUPLICATE_OF = "duplicateOf";
    static final String CONFIDENCE = "confidence";
    static final String APPNAME = "appName";
    static final String APPVER = "appVer";
    static final String OSNAME = "osName";
    static final String OSVER = "osVer";
    static final String USERNAME = "username";
    /* "retrieveSign" method & parameter */
    static final String RETRIEVE_SIGN = "retrieveSign";

    static final String ID = "id";
    /* "addComment" method & paramaters */
    static final String ADD_COMMENT = "addComment";

    static final String SIGN_ID = "signId";
    static final String TEXT = "text";
    /* "addComments" method & parameters */
    static final String ADD_COMMENTS = "addComments";

    static final String SIGN_IDS = "signIds";

    private Constants() {}
}