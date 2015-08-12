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


/**
 * Defines the attributes of a bounding box. A bounding box represents a searching area and is represented by four GEO
 * coordinate: upper latitude, lower latitude, upper longitude and lower longitude.
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

    private final double north;
    private final double south;
    private final double east;
    private final double west;


    /**
     * Builds a bounding box with the given arguments. Latitude values should belong to the interval [-90.0,90.0].
     * Longitude values should belong to the interval [-180.0,180.0]. Invalid values will be normalized.
     *
     * @param north the northern border, given as decimal degrees
     * @param south the southern border, given as decimal degrees
     * @param east the eastern border, given as decimal degrees
     * @param west the western border, given as decimal degrees
     */
    public BoundingBox(final double north, final double south, final double east, final double west) {
        this.north = north > MAX_LAT ? MAX_LAT : north;
        this.south = south < MIN_LAT ? MIN_LAT : south;
        this.east = east > MAX_LON ? MAX_LON : east;
        this.west = west < MIN_LON ? MIN_LON : west;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof BoundingBox) {
            final BoundingBox other = (BoundingBox) obj;
            if (equals(east, other.getEast()) && equals(north, other.getNorth()) && equals(south, other.getSouth())
                    && equals(west, other.getWest())) {
                result = true;
            }
        }
        return result;
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

    private boolean equals(final double obj1, final double obj2) {
        return Double.doubleToLongBits(obj1) == Double.doubleToLongBits(obj2);
    }
}