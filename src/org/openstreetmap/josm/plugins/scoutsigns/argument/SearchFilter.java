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
package org.openstreetmap.josm.plugins.scoutsigns.argument;

import java.util.List;
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

    public static final Double DEF_CONFIDENCE = 90.0;

    private final TimestampFilter timestampFilter;
    private final List<String> types;
    private Status status;
    private Long duplicateOf;
    private final Double confidence;
    private Application app;
    private Device device;
    private String username;


    /**
     * Builds a new filter with the given arguments.
     *
     * @param timestampFilter defines the interval of time in which the returned road signs have been created
     * @param types a list of road sign type
     * @param confidence specifies the confidence with which the sign has been recognized (0-300)
     */
    public SearchFilter(final TimestampFilter timestampFilter, final List<String> types, final Double confidence) {
        this.timestampFilter = timestampFilter;
        this.types = types;
        this.confidence = confidence;
    }

    /**
     * Builds a new filter with the given arguments.
     *
     * @param timestampFilter defines the interval of time in which the returned road signs have been created
     * @param types a list of road sign type
     * @param status the status of the returned road signs
     * @param duplicateOf the id of road sign of which all returned road signs are duplicates
     * @param confidence specifies the confidence with which the sign has been recognized (0-300)
     * @param app defines the application from which the returned road sign have been created
     * @param device defines the device from which the returned road sign have been created
     * @param username the user's OSM user name
     */
    public SearchFilter(final TimestampFilter timestampFilter, final List<String> types, final Status status,
            final Long duplicateOf, final Double confidence, final Application app, final Device device,
            final String username) {
        this(timestampFilter, types, confidence);
        this.status = status;
        this.duplicateOf = duplicateOf;
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
            result = result && ObjectUtil.bothNullOrEqual(timestampFilter, other.getTimestampFilter());
            result = result && ObjectUtil.bothNullOrEqual(status, other.getStatus());
            result = result && ObjectUtil.bothNullOrEqual(types, other.getTypes());
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

    public Double getConfidence() {
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

    public List<String> getTypes() {
        return types;
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
        result = prime * result + ObjectUtil.hashCode(types);
        result = prime * result + ObjectUtil.hashCode(username);
        return result;
    }
}