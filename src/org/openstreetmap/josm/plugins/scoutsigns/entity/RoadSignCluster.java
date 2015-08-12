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
package org.openstreetmap.josm.plugins.scoutsigns.entity;

import org.openstreetmap.josm.data.coor.LatLon;


/**
 * Defines the road sign cluster business entity.
 *
 * @author Beata
 * @version $Revision$
 */
public class RoadSignCluster {

    private final LatLon position;
    private final Integer count;


    /**
     * Builds a new {@code RoadSignCluster} with the given arguments.
     *
     * @param position the geographical position where the cluster should be represented on the map
     * @param count the number of road signs contained in the cluster
     */
    public RoadSignCluster(final LatLon position, final Integer count) {
        this.position = position;
        this.count = count;
    }


    public Integer getCount() {
        return count;
    }

    public LatLon getPosition() {
        return position;
    }
}