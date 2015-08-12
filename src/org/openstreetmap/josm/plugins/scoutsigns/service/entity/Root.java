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
package org.openstreetmap.josm.plugins.scoutsigns.service.entity;

import java.util.List;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSignCluster;


/**
 * Represents the root of the response content returned by the FcdSignService.
 *
 * @author Beata
 * @version $Revision$
 */
public class Root {

    private Status status;
    private List<RoadSign> roadSigns;
    private List<RoadSignCluster> roadSignClusters;
    private RoadSign roadSign;

    /**
     * Builds an empty {@code Root}
     */
    public Root() {}

    /**
     * Builds a new {@code Root} with he given argument.
     *
     * @param status represents the status information
     */
    public Root(final Status status) {
        this.status = status;
    }

    /**
     * Builds a new {@code Root} with he given arguments.
     *
     * @param status represents the status information
     * @param roadSigns a list of road signs
     * @param roadSignClusters a list of road sign clusters
     */
    public Root(final Status status, final List<RoadSign> roadSigns, final List<RoadSignCluster> roadSignClusters) {
        this(status);
        this.roadSigns = roadSigns;
        this.roadSignClusters = roadSignClusters;
    }

    /**
     * Builds a new {@code Root} with the given arguments.
     *
     * @param status represents the status information
     * @param roadSign a {@code RoadSign}
     */
    public Root(final Status status, final RoadSign roadSign) {
        this.status = status;
        this.roadSign = roadSign;
    }


    public RoadSign getRoadSign() {
        return roadSign;
    }

    public List<RoadSignCluster> getRoadSignClusters() {
        return roadSignClusters;
    }

    public List<RoadSign> getRoadSigns() {
        return roadSigns;
    }

    public Status getStatus() {
        return status;
    }
}