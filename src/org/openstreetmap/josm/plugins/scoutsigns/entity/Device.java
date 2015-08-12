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


/**
 * Defines the device business entity. Represents information about the used device.
 *
 * @author Beata
 * @version $Revision$
 */
public class Device {

    private final String osName;
    private final String osVersion;


    /**
     * Builds a new object with the given arguments.
     *
     * @param osName the name of the operating system
     * @param osVersion the version of the operating system
     */
    public Device(final String osName, final String osVersion) {
        this.osName = osName;
        this.osVersion = osVersion;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof Device) {
            final Device other = (Device) obj;
            result = ObjectUtil.bothNullOrEqual(osName, other.getOsName());
            result = result && ObjectUtil.bothNullOrEqual(osVersion, other.getOsVersion());
        }
        return result;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ObjectUtil.hashCode(osName);
        result = prime * result + ObjectUtil.hashCode(osVersion);
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (osName != null) {
            sb.append(osName);
        }
        if (osVersion != null) {
            sb.append(", ").append(osVersion);
        }
        return sb.toString();
    }
}