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
package org.openstreetmap.josm.plugins.scoutsigns.argument;


/**
 * Defines the attributes of a bounding box. A bounding box represents a
 * searching area and is represented by four GEO coordinate: upper latitude,
 * lower latitude, upper longitude and lower longitude.
 * 
 * @author Beata
 * @version $Revision$
 */
public class BoundingBox {
    
    /* longitude interval limits */
    private static final double MIN_LON = -180.0;
    private static final double MAX_LON = 180.0;
    
    /* latitude interval limits */
    private static final double MIN_LAT = -90.0;
    private static final double MAX_LAT = 90.0;
    
    private double north;
    private double south;
    private double east;
    private double west;
    
    
    /**
     * Builds a bounding box with the given arguments. Latitude values should
     * belong to the interval [-90.0,90.0]. Longitude values should belong to
     * the interval [-180.0,180.0]. Invalid values will be normalized.
     * 
     * @param north the northern border, given as decimal degrees
     * @param south the southern border, given as decimal degrees
     * @param east the eastern border, given as decimal degrees
     * @param west the western border, given as decimal degrees
     */
    public BoundingBox(double north, double south, double east, double west) {
        this.north = north > MAX_LAT ? MAX_LAT : north;
        this.south = south < MIN_LAT ? MIN_LAT : south;
        this.east = east > MAX_LON ? MAX_LON : east;
        this.west = west < MIN_LON ? MIN_LON : west;
    }
    
    
    /**
     * Returns the northern corner of the bounding box
     * 
     * @return a double value
     */
    public double getNorth() {
        return north;
    }
    
    /**
     * Returns the southern corner of the bounding box
     * 
     * @return a double value
     */
    public double getSouth() {
        return south;
    }
    
    /**
     * Returns the eastern corner of the bounding box
     * 
     * @return a double value
     */
    public double getEast() {
        return east;
    }
    
    /**
     * Returns the western corner of the bounding box
     * 
     * @return a double value
     */
    public double getWest() {
        return west;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        final int bit = 32;
        int result = 1;
        long temp = Double.doubleToLongBits(east);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        temp = Double.doubleToLongBits(north);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        temp = Double.doubleToLongBits(south);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        temp = Double.doubleToLongBits(west);
        result = prime * result + (int) (temp ^ (temp >>> bit));
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof BoundingBox) {
            final BoundingBox other = (BoundingBox) obj;
            if (equals(east, other.getEast())
                    && equals(north, other.getNorth())
                    && equals(south, other.getSouth())
                    && equals(west, other.getWest())) {
                result = true;
            }
        }
        return result;
    }
    
    private boolean equals(double obj1, double obj2) {
        return Double.doubleToLongBits(obj1) == Double.doubleToLongBits(obj2);
    }
}