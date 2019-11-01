/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
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