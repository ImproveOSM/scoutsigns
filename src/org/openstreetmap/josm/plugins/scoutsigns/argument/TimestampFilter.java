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

import com.telenav.josm.common.util.EntityUtil;


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