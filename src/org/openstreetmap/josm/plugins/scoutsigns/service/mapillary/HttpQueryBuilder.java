/*
 * Copyright (c) 2015 SKOBBLER SRL.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with SKOBBLER SRL.
 *
 * Created on Mar 11, 2015 by Beata
 * Modified on $Date$
 *          by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.service.mapillary;

import java.util.List;
import org.openstreetmap.josm.plugins.scoutsigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.MapillaryConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpUtil;


/**
 * Helper class, builds Mapillary API specific queries.
 *
 * @author Beata
 * @version $Revision$
 */
final class HttpQueryBuilder {

    private static final char EQ = '=';
    private static final char AND = '&';
    private static final char QUESTIONM = '?';

    private StringBuilder query;


    private void addBBoxFilter(final BoundingBox bbox) {
        query.append(AND);
        query.append(Constants.MIN_LAT).append(EQ).append(bbox.getSouth());
        query.append(AND);
        query.append(Constants.MAX_LAT).append(EQ).append(bbox.getNorth());
        query.append(AND);
        query.append(Constants.MIN_LON).append(EQ).append(bbox.getWest());
        query.append(AND);
        query.append(Constants.MAX_LON).append(EQ).append(bbox.getEast());
    }

    private void addLimitFilter() {
        query.append(AND);
        query.append(Constants.LIMIT).append(EQ);
        query.append(MapillaryConfig.getInstance().getLimit());
    }

    private void addMinScoreFilter(final Double score) {
        if (score != null) {
            query.append(AND).append(Constants.MIN_SCORE);
            query.append(EQ).append(score);
        }
    }

    private void addTimestampFilter(final TimestampFilter tsFilter) {
        if (tsFilter != null) {
            if (tsFilter.getFrom() != null) {
                query.append(AND);
                query.append(Constants.START_TIME).append(EQ);
                query.append(tsFilter.getFrom());
            }
            if (tsFilter.getTo() != null) {
                query.append(AND);
                query.append(Constants.END_TIME).append(EQ);
                query.append(tsFilter.getTo());
            }
        }
    }

    private void addTypeFilter(final List<String> types) {
        if (types != null && !types.isEmpty()) {
            // need to add the parameter for each type
            for (final String type : types) {
                query.append(AND);
                query.append(Constants.TYPE).append(EQ);
                query.append(HttpUtil.utf8Encode(type));
            }
        }
    }

    private String build(final String method) {
        final StringBuilder url = new StringBuilder(MapillaryConfig.getInstance().getApiUrl());
        url.append(method).append(QUESTIONM);
        url.append(Constants.CLIENT_ID).append(EQ).append(MapillaryConfig.getInstance().getApiKey());
        url.append(query);
        return url.toString();
    }

    /**
     * Builds a new HTTP search query with the given arguments.
     *
     * @param bbox the searching area
     * @param filterPack a {@code FilterPack} defining the searching filters
     * @return a {@code String} representing the HTTP query
     */
    String buildSearchQuery(final BoundingBox bbox, final SearchFilter  filterPack) {
        query = new StringBuilder();

        // add bounding box
        addBBoxFilter(bbox);
        if (filterPack != null) {
            addTimestampFilter(filterPack.getTimestampFilter());
            addTypeFilter(filterPack.getTypes());
            addMinScoreFilter(filterPack.getConfidence());
        }

        // add pagination
        addLimitFilter();

        // build query
        return build(Constants.SEARCH);
    }
}