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
        this.roadSigns = roadSigns != null ? roadSigns : new ArrayList<RoadSign>();
        this.roadSignClusters = roadSignClusters != null ? roadSignClusters : new ArrayList<RoadSignCluster>();
    }


    public List<RoadSignCluster> getRoadSignClusters() {
        return roadSignClusters;
    }

    public List<RoadSign> getRoadSigns() {
        return roadSigns;
    }
}