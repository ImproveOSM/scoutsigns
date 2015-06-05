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
package org.openstreetmap.josm.plugins.scoutsigns.argument;

import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.ObjectUtil;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;


/**
 * Defines the filters that can be applied to the "searchSings" method.
 *
 * @author Beata
 * @version $Revision$
 */
public class SearchFilter {

    public static final short DEF_CONFIDENCE = 90;

    private final TimestampFilter timestampFilter;
    private final String type;
    private final Status status;
    private final Long duplicateOf;
    private final Short confidence;
    private final Application app;
    private final Device device;
    private final String username;


    /**
     * Builds a new filter with the given arguments.
     *
     * @param timestampFilter defines the interval of time in which the returned road signs have been created
     * @param type the type of the returned road signs
     * @param status the status of the returned road signs
     * @param duplicateOf the id of road sign of which all returned road signs are duplicates
     * @param app defines the application from which the returned road sign have been created
     * @param device defines the device from which the returned road sign have been created
     * @param username the user's OSM user name
     */
    public SearchFilter(final TimestampFilter timestampFilter, final String type, final Status status,
            final Long duplicateOf, final Short confidence, final Application app, final Device device,
            final String username) {
        this.timestampFilter = timestampFilter;
        this.type = type;
        this.status = status;
        this.duplicateOf = duplicateOf;
        this.confidence = confidence;
        this.app = app;
        this.device = device;
        this.username = username;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof SearchFilter) {
            final SearchFilter other = (SearchFilter) obj;
            result = ObjectUtil.bothNullOrEqual(timestampFilter, other.getTimestampFilter());
            result = result && ObjectUtil.bothNullOrEqual(status, other.getStatus());
            result = result && ObjectUtil.bothNullOrEqual(type, other.getType());
            result = result && ObjectUtil.bothNullOrEqual(duplicateOf, other.getDuplicateOf());
            result = result && ObjectUtil.bothNullOrEqual(confidence, other.getConfidence());
            result = result && ObjectUtil.bothNullOrEqual(app, other.getApp());
            result = result && ObjectUtil.bothNullOrEqual(device, other.getDevice());
            result = result && ObjectUtil.bothNullOrEqual(username, other.getUsername());
        }
        return result;
    }

    public Application getApp() {
        return app;
    }

    public Short getConfidence() {
        return confidence;
    }

    public Device getDevice() {
        return device;
    }

    public Long getDuplicateOf() {
        return duplicateOf;
    }

    public Status getStatus() {
        return status;
    }

    public TimestampFilter getTimestampFilter() {
        return timestampFilter;
    }

    public String getType() {
        return type;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ObjectUtil.hashCode(app);
        result = prime * result + ObjectUtil.hashCode(device);
        result = prime * result + ObjectUtil.hashCode(duplicateOf);
        result = prime * result + ObjectUtil.hashCode(confidence);
        result = prime * result + ObjectUtil.hashCode(status);
        result = prime * result + ObjectUtil.hashCode(timestampFilter);
        result = prime * result + ObjectUtil.hashCode(type);
        result = prime * result + ObjectUtil.hashCode(username);
        return result;
    }
}