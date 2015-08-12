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
 * Defines the 'sign position' business entity. A sign position represents the geographical position of a road sign.
 *
 * @author Beata
 * @version $Revision$
 */
public class SignPosition {

    private final LatLon position;
    private final Double height;


    /**
     * Builds a new object with the given arguments.
     *
     * @param position the road sign's position
     * @param height the height of the sign in meters relative to the vehicle
     */
    public SignPosition(final LatLon position, final Double height) {
        this.position = position;
        this.height = height;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof SignPosition) {
            final SignPosition other = (SignPosition) obj;

            result = ObjectUtil.bothNullOrEqual(height, other.getHeight());
            result = result && ObjectUtil.bothNullOrEqual(position, other.getPosition());
        }
        return result;
    }

    public Double getHeight() {
        return height;
    }

    public LatLon getPosition() {
        return position;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ObjectUtil.hashCode(height);
        result = prime * result + ObjectUtil.hashCode(position);
        return result;
    }
}