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
package org.openstreetmap.josm.plugins.scoutsigns.service;

import org.openstreetmap.josm.plugins.scoutsigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpUtil;


/**
 * Helper class, builds HTTP request queries.
 * 
 * @author Beata
 * @version $Revision$
 */
class HttpQueryBuilder {
    
    private static final char QUESTIONM = '?';
    private static final char EQ = '=';
    private static final char AND = '&';
    private StringBuilder query;
    
    
    HttpQueryBuilder() {}
    
    /**
     * Creates a {@code HttpQueryBuilder} with the given arguments.
     * 
     * @param bbox specify the current search area
     * @param filter specify the current search filters
     * @param zoom specify the current zoom level
     */
    HttpQueryBuilder(BoundingBox bbox, SearchFilter filter, int zoom) {
        query = new StringBuilder();
        
        // add mandatory filters
        addFormatFilter();
        addBBoxFilter(bbox);
        addZoomFilter(zoom);
        
        // add optional filters
        if (filter != null) {
            addTimestampFilter(filter.getTimestampFilter());
            addStatusFilter(filter.getStatus());
            addTypeFilter(filter.getType());
            addDuplicateFilter(filter.getDuplicateOf());
            addConfidenceFilter(filter.getConfidence());
            addApplicationCtiteria(filter.getApp());
            addDeviceFilter(filter.getDevice());
            
            addUsernameFilter(filter.getUsername());
        }
    }
    
    /**
     * Creates a new builder with the given argument.
     * 
     * @param id a unique identifier
     */
    HttpQueryBuilder(Long id) {
        query = new StringBuilder();
        
        addFormatFilter();
        addIdFilter(id);
    }
    
    
    /**
     * Builds a new HTTP query for the specified method with the currently set
     * fields.
     * 
     * @param method specifies a valid FcdSignService method
     * @return a {@code String} object
     */
    String build(String method) {
        StringBuilder url = new StringBuilder(
                ServiceCnf.getInstance().getServiceUrl());
        url.append(method).append(QUESTIONM);
        url.append(query);
        return url.toString();
    }
    
    
    private void addFormatFilter() {
        query.append(Constants.FORMAT).append(EQ).append(Constants.FORMAT_VAL);
    }
    
    private void addBBoxFilter(BoundingBox bbox) {
        query.append(AND);
        query.append(Constants.NORTH).append(EQ).append(bbox.getNorth());
        query.append(AND);
        query.append(Constants.SOUTH).append(EQ).append(bbox.getSouth());
        query.append(AND);
        query.append(Constants.EAST).append(EQ).append(bbox.getEast());
        query.append(AND);
        query.append(Constants.WEST).append(EQ).append(bbox.getWest());
    }
    
    private void addZoomFilter(int zoom) {
        query.append(AND);
        query.append(Constants.ZOOM).append(EQ).append(zoom);
    }
    
    private void addTimestampFilter(TimestampFilter tsFilter) {
        if (tsFilter != null) {
            if (tsFilter.getFrom() != null) {
                query.append(AND);
                query.append(Constants.FROM).append(EQ).append(tsFilter.getFrom());
            }
            if (tsFilter.getTo() != null) {
                query.append(AND);
                query.append(Constants.TO).append(EQ).append(tsFilter.getTo());
            }
        }
    }
    
    private void addTypeFilter(String type) {
        if (type != null) {
            query.append(AND);
            query.append(Constants.TYPE).append(EQ);
            query.append(HttpUtil.utf8Encode(type));
        }
    }
    
    private void addStatusFilter(Status status) {
        if (status != null) {
            query.append(AND);
            query.append(Constants.STATUS).append(EQ);
            query.append(HttpUtil.utf8Encode(status.name()));
        }
    }
    
    private void addDuplicateFilter(Long duplicateOf) {
        if (duplicateOf != null) {
            query.append(AND);
            query.append(Constants.DUPLICATE_OF).append(EQ).append(duplicateOf);
        }
    }
    
    private void addConfidenceFilter(Short confidence) {
        if (confidence != null) {
            query.append(AND);
            query.append(Constants.CONFIDENCE).append(EQ).append(confidence);
        }
    }
    
    private void addApplicationCtiteria(Application app) {
        if (app != null) {
            if (app.getName() != null && !app.getName().isEmpty()) {
                query.append(AND);
                query.append(Constants.APPNAME).append(EQ);
                query.append(HttpUtil.utf8Encode(app.getName()));
            }
            
            if (app.getVersion() != null && !app.getVersion().isEmpty()) {
                query.append(AND);
                query.append(Constants.APPVER).append(EQ);
                query.append(HttpUtil.utf8Encode(app.getVersion()));
            }
        }
    }
    
    private void addDeviceFilter(Device device) {
        if (device != null) {
            if (device.getOsName() != null && !device.getOsName().isEmpty()) {
                query.append(AND);
                query.append(Constants.OSNAME).append(EQ);
                query.append(HttpUtil.utf8Encode(device.getOsName()));
            }
            if (device.getOsVersion() != null
                    && !device.getOsVersion().isEmpty()) {
                query.append(AND);
                query.append(Constants.OSVER).append(EQ);
                query.append(HttpUtil.utf8Encode(device.getOsVersion()));
            }
        }
    }
    
    private void addUsernameFilter(String username) {
        if (username != null && !username.isEmpty()) {
            query.append(AND);
            query.append(Constants.USERNAME).append(EQ);
            query.append(HttpUtil.utf8Encode(username));
        }
    }
    
    private void addIdFilter(Long id) {
        query.append(AND);
        query.append(Constants.ID).append(EQ).append(id);
    }
}