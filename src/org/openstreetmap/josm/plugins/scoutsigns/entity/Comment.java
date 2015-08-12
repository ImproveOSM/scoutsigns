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
package org.openstreetmap.josm.plugins.scoutsigns.entity;


/**
 * Defines the comment business entity.
 *
 * @author Beata
 * @version $Revision$
 */
public class Comment {

    private final String username;
    private final Long tstamp;
    private final String text;
    private final Status status;
    private final Long duplicateOf;


    /**
     * Builds a new object with the given arguments.
     *
     * @param username the OSM user name of the user who posted the comment
     * @param tstamp the timestamp at which the comment has been posted, given in "Unix time" format
     * @param text the text of the comment
     * @param status the {@code Status} set to the road sign by this comment. This attribute may be missing if the
     * comment has not changed the status.
     * @param duplicateOf the id of the road sign of which this road sign is a duplicate of. This attribute is only
     * present if the status is {@code Status#DUPLICATE}
     */
    public Comment(final String username, final Long tstamp, final String text, final Status status,
            final Long duplicateOf) {
        this.username = username;
        this.text = text;
        this.status = status;
        this.duplicateOf = duplicateOf;
        this.tstamp = tstamp;
    }


    public Long getDuplicateOf() {
        return duplicateOf;
    }

    public Status getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public Long getTstamp() {
        return tstamp;
    }

    public String getUsername() {
        return username;
    }
}