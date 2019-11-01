/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.service;

import java.util.List;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import com.telenav.josm.common.argument.BoundingBox;
import com.telenav.josm.common.http.HttpUtil;


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
    private static final Long UNIX_TSTP = 1000L;
    private StringBuilder query;


    HttpQueryBuilder() {}

    /**
     * Creates a {@code HttpQueryBuilder} with the given arguments.
     *
     * @param bbox specify the current search area
     * @param filter specify the current search filters
     * @param zoom specify the current zoom level
     */
    HttpQueryBuilder(final BoundingBox bbox, final int zoom) {
        query = new StringBuilder();

        // add mandatory filters
        addFormatFilter();
        addBBoxFilter(bbox);
        addZoomFilter(zoom);
    }

    HttpQueryBuilder(final BoundingBox bbox, final SearchFilter filter, final int zoom) {
        query = new StringBuilder();

        // add mandatory filters
        addFormatFilter();
        addBBoxFilter(bbox);
        addZoomFilter(zoom);

        // add filters
        if (zoom > Config.getInstance().getMaxClusterZoom() && filter != null) {
            addTimestampFilter(filter.getTimestampFilter());
            addStatusFilter(filter.getStatus());
            addTypeFilter(filter.getTypes());
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
    HttpQueryBuilder(final Long id) {
        query = new StringBuilder();

        addFormatFilter();
        addIdFilter(id);
    }


    private void addApplicationCtiteria(final Application app) {
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


    private void addBBoxFilter(final BoundingBox bbox) {
        query.append(AND);
        query.append(Constants.NORTH).append(EQ).append(bbox.getNorth());
        query.append(AND);
        query.append(Constants.SOUTH).append(EQ).append(bbox.getSouth());
        query.append(AND);
        query.append(Constants.EAST).append(EQ).append(bbox.getEast());
        query.append(AND);
        query.append(Constants.WEST).append(EQ).append(bbox.getWest());
    }

    private void addConfidenceFilter(final Double confidence) {
        if (confidence != null) {
            query.append(AND);
            query.append(Constants.CONFIDENCE).append(EQ).append(confidence.shortValue());
        }
    }

    private void addDeviceFilter(final Device device) {
        if (device != null) {
            if (device.getOsName() != null && !device.getOsName().isEmpty()) {
                query.append(AND);
                query.append(Constants.OSNAME).append(EQ);
                query.append(HttpUtil.utf8Encode(device.getOsName()));
            }

            if (device.getOsVersion() != null && !device.getOsVersion().isEmpty()) {
                query.append(AND);
                query.append(Constants.OSVER).append(EQ);
                query.append(HttpUtil.utf8Encode(device.getOsVersion()));
            }
        }
    }

    private void addDuplicateFilter(final Long duplicateOf) {
        if (duplicateOf != null) {
            query.append(AND);
            query.append(Constants.DUPLICATE_OF).append(EQ).append(duplicateOf);
        }
    }

    private void addFormatFilter() {
        query.append(Constants.FORMAT).append(EQ).append(Constants.FORMAT_VAL);
    }

    private void addIdFilter(final Long id) {
        query.append(AND);
        query.append(Constants.ID).append(EQ).append(id);
    }

    private void addStatusFilter(final Status status) {
        if (status != null) {
            query.append(AND);
            query.append(Constants.STATUS).append(EQ);
            query.append(HttpUtil.utf8Encode(status.name()));
        }
    }

    private void addTimestampFilter(final TimestampFilter tsFilter) {
        if (tsFilter != null) {
            if (tsFilter.getFrom() != null) {
                query.append(AND);
                query.append(Constants.FROM).append(EQ);
                query.append(tsFilter.getFrom() / UNIX_TSTP);
            }
            if (tsFilter.getTo() != null) {
                query.append(AND);
                query.append(Constants.TO).append(EQ);
                query.append(tsFilter.getTo() / UNIX_TSTP);
            }
        }
    }

    private void addTypeFilter(final List<String> types) {
        if (types != null && !types.isEmpty()) {
            query.append(AND);
            query.append(Constants.TYPES).append(EQ);
            query.append(HttpUtil.utf8Encode(types));
        }
    }

    private void addUsernameFilter(final String username) {
        if (username != null && !username.isEmpty()) {
            query.append(AND);
            query.append(Constants.USERNAME).append(EQ);
            query.append(HttpUtil.utf8Encode(username));
        }
    }

    private void addZoomFilter(final int zoom) {
        query.append(AND);
        query.append(Constants.ZOOM).append(EQ).append(zoom);
    }

    /**
     * Builds a new HTTP query for the specified method with the currently set fields.
     *
     * @param method specifies a valid FcdSignService method
     * @return a {@code String} object
     */
    String build(final String method) {
        final StringBuilder url = new StringBuilder(Config.getInstance().getServiceUrl());
        url.append(method).append(QUESTIONM);
        if (query != null && query.length() > 0) {
            url.append(query);
        }
        return url.toString();
    }
}