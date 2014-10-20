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
 * Created on Jul 29, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.service.entity;

import java.util.List;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;


/**
 * Represents the root of the response content returned by the FcdSignService.
 * 
 * @author Beata
 * @version $Revision$
 */
public class Root {
    
    private Status status;
    private List<RoadSign> roadSigns;
    private RoadSign roadSign;
    
    
    public Root() {}
    
    /**
     * Builds a new {@code Root} with he given argument.
     * 
     * @param status represents the status information
     */
    public Root(Status status) {
        this.status = status;
    }
    
    /**
     * Builds a new {@code Root} with he given arguments.
     * 
     * @param status represents the status information
     * @param roadSigns a collection of {@code RoadSigns}
     */
    public Root(Status status, List<RoadSign> roadSigns) {
        this(status);
        this.roadSigns = roadSigns;
    }
    
    /**
     * Builds a new {@code Root} with the given arguments.
     * 
     * @param status represents the status information
     * @param roadSign a {@code RoadSign}
     */
    public Root(Status status, RoadSign roadSign) {
        this.status = status;
        this.roadSign = roadSign;
    }
    
    
    public Status getStatus() {
        return status;
    }
    
    public List<RoadSign> getRoadSigns() {
        return roadSigns;
    }
    
    public RoadSign getRoadSign() {
        return roadSign;
    }
}