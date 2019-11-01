/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the data set of the {@code ScoutSignsLayer}.
 *
 * @author Beata
 * @version $Revision$
 */
public class DataSet {

    private final List<RoadSign> roadSigns;
    private final List<RoadSignCluster> roadSignClusters;

    /**
     * Builds an empty {@code DataSet}
     */
    public DataSet() {
        this.roadSigns = new ArrayList<>();
        this.roadSignClusters = new ArrayList<>();
    }

    /**
     * Builds a new @ code DataSet} with the given arguments.
     *
     * @param roadSigns a list of {@code RoadSign}s.
     * @param roadSignClusters a lit of {@code RoadSignCluster}s.
     */
    public DataSet(final List<RoadSign> roadSigns, final List<RoadSignCluster> roadSignClusters) {
        this.roadSigns = roadSigns != null ? roadSigns : new ArrayList<>();
        this.roadSignClusters = roadSignClusters != null ? roadSignClusters : new ArrayList<>();
    }


    public List<RoadSignCluster> getRoadSignClusters() {
        return roadSignClusters;
    }

    public List<RoadSign> getRoadSigns() {
        return roadSigns;
    }
}