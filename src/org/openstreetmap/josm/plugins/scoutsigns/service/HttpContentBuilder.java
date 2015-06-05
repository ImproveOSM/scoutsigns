/*
 * Copyright (c) 2014, skobbler GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Oct 16, 2014 by Beata
 * Modified on $DateTime$ by $Author$
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


    Map<String, String> getContent() {
        return content;
    }
}