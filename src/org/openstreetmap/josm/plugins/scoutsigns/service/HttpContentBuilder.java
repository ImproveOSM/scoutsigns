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
package org.openstreetmap.josm.plugins.scoutsigns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;


/**
 * Helper class, builds the content for the HTTP POST methods.
 *
 * @author Beata
 * @version $Revision$
 */
class HttpContentBuilder {

    private static final char SEP = ',';

    private final Map<String, String> content = new HashMap<>();


    private HttpContentBuilder(final String username, final String text, final Status status, final Long duplicateOf) {
        content.put(Constants.USERNAME, username);
        content.put(Constants.TEXT, text);
        if (status != null) {
            content.put(Constants.STATUS, status.name());
        }
        if (duplicateOf != null) {
            content.put(Constants.DUPLICATE_OF, duplicateOf.toString());
        }
    }

    /**
     * Builds a new builder with the given arguments.
     *
     * @param signIds a collection of sign identifiers
     * @param username the user's OSM username
     * @param text the comment text
     * @param status a {@code Status}
     * @param duplicateOf a sign identifier
     */
    HttpContentBuilder(final List<Long> signIds, final String username, final String text, final Status status,
            final Long duplicateOf) {
        this(username, text, status, duplicateOf);
        final StringBuilder sb = new StringBuilder();
        for (final Long id : signIds) {
            sb.append(id).append(SEP);
        }
        sb.delete(sb.length() - 1, sb.length());
        content.put(Constants.SIGN_IDS, sb.toString());
    }

    /**
     * Builds a new builder with the given arguments.
     *
     * @param signId the sign's identifier
     * @param username the user's OSM username
     * @param text the comment text
     * @param status a {@code Status}
     * @param duplicateOf a sign identifier
     */
    HttpContentBuilder(final Long signId, final String username, final String text, final Status status,
            final Long duplicateOf) {
        this(username, text, status, duplicateOf);
        content.put(Constants.SIGN_ID, signId.toString());
    }

    Map<String, String> getContent() {
        return content;
    }
}