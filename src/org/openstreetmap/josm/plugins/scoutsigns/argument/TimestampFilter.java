/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.argument;

import com.telenav.josm.common.entity.EntityUtil;


/**
 * Timestamp filter for the search road sign method. This filter defines an interval of time in which the returned road
 * signs have been created. If any filed of this class is null then it will not be considered as a search criteria.
 *
 * @author Bea
 * @version $Revision$
 */
public class TimestampFilter {

    private final Long from;
    private final Long to;


    /**
     * Builds a new {@code TimestampFilter} with the given arguments.
     *
     * @param from the start timestamp in 'Unix time' format
     * @param to the end timestamp in 'Unix time' format
     */
    public TimestampFilter(final Long from, final Long to) {
        this.from = from;
        this.to = to;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof TimestampFilter) {
            final TimestampFilter other = (TimestampFilter) obj;
            result = EntityUtil.bothNullOrEqual(from, other.getFrom());
            result = result && EntityUtil.bothNullOrEqual(to, other.getTo());
        }
        return result;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + EntityUtil.hashCode(from);
        result = prime * result + EntityUtil.hashCode(to);
        return result;
    }
}