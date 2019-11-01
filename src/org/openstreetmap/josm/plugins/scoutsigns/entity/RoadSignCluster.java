/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
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