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
 * Created on Jul 16, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.entity;


/**
 * Defines the comment business entity.
 * 
 * @author Beata
 * @version $Revision$
 */
public class Comment {
    
    private String username;
    private Long tstamp;
    private String text;
    private Status status;
    private Long duplicateOf;
    
    
    /**
     * Builds a new object with the given arguments.
     * 
     * @param username the OSM user name of the user who posted the comment
     * @param tstamp the timestamp at which the comment has been posted, given
     * in "Unix time" format
     * @param text the text of the comment
     * @param status the {@code Status} set to the road sign by this comment.
     * This attribute may be missing if the comment has not changed the status.
     * @param duplicateOf the id of the road sign of which this road sign is a
     * duplicate of. This attribute is only present if the status is
     * {@code Status#DUPLICATE}
     */
    public Comment(String username, Long tstamp, String text, Status status,
            Long duplicateOf) {
        this.username = username;
        this.text = text;
        this.status = status;
        this.duplicateOf = duplicateOf;
        this.tstamp = tstamp;
    }
    
    
    public String getUsername() {
        return username;
    }
    
    public Long getTstamp() {
        return tstamp;
    }
    
    public String getText() {
        return text;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public Long getDuplicateOf() {
        return duplicateOf;
    }
}