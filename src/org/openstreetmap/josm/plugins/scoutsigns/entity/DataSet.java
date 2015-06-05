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
 * Created on Jan 9, 2015 by Beata
 * Modified on $DateTime$ by $Author$
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