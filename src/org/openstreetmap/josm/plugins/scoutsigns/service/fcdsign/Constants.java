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
package org.openstreetmap.josm.plugins.scoutsigns.service.fcdsign;


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