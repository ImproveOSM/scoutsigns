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
 * Created on Jul 29, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.skosigns.argument;

import org.openstreetmap.josm.plugins.skosigns.entity.Application;
import org.openstreetmap.josm.plugins.skosigns.entity.Device;
import org.openstreetmap.josm.plugins.skosigns.entity.Status;


/**
 * Defines the filters that can be applied to the "searchSings" method.
 * @author Beata
 * @version $Revision$
 */
public class SearchFilter {
    
    private TimestampFilter timestampFilter;
    private String type;
    private Status status;
    private Long duplicateOf;
    private Application app;
    private Device device;
    private String username;
    
    
    /**
     * Builds a new filter with the given arguments.
     * 
     * @param timestampFilter defines the interval of time in which the returned
     * road signs have been created
     * @param type the type of the returned road signs
     * @param status the status of the returned road signs
     * @param duplicateOf the id of road sign of which all returned road signs
     * are duplicates
     * @param app defines the application from which the returned road sign have
     * been created
     * @param device defines the device from which the returned road sign have
     * been created
     * @param username the user's OSM user name
     */
    public SearchFilter(TimestampFilter timestampFilter, String type,
            Status status, Long duplicateOf, Application app, Device device,
            String username) {
        this.timestampFilter = timestampFilter;
        this.type = type;
        this.status = status;
        this.duplicateOf = duplicateOf;
        this.app = app;
        this.device = device;
        this.username = username;
    }
    
    
    public TimestampFilter getTimestampFilter() {
        return timestampFilter;
    }
    
    public String getType() {
        return type;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public Long getDuplicateOf() {
        return duplicateOf;
    }
    
    public Application getApp() {
        return app;
    }
    
    public Device getDevice() {
        return device;
    }
    
    public String getUsername() {
        return username;
    }
}