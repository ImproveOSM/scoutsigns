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
 * Created on Jul 16, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.entity;

import org.openstreetmap.josm.data.coor.LatLon;


/**
 * Defines the 'car position' business entity. A car position represents the
 * geographic position of the vehicle at the time of the road sign creation.
 * 
 * @author Beata
 * @version $Revision$
 */
public class CarPosition {
    
    private LatLon position;
    private Integer heading;
    private Integer accuracy;
    private String type;
    
    
    /**
     * Builds a new object with the given arguments.
     * 
     * @param position the vehicle geographic position
     * @param heading the direction the vehicle was facing
     * @param accuracy the accuracy of the latitude and longitude in meters
     * @param type the type of the position (the interpolated GPS position or
     * the last known GPS position)
     */
    public CarPosition(LatLon position, Integer heading, Integer accuracy,
            String type) {
        this.position = position;
        this.heading = heading;
        this.accuracy = accuracy;
        this.type = type;
    }
    
    
    public LatLon getPosition() {
        return position;
    }
    
    public Integer getHeading() {
        return heading;
    }
    
    public Integer getAccuracy() {
        return accuracy;
    }
    
    public String getType() {
        return type;
    }
}