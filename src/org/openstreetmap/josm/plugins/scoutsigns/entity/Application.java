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
 * Defines the application business entity.
 *
 * @author Beata
 * @version $Revision$
 */
public class Application {

    private final String name;
    private final String version;


    /**
     * Builds a new object with the given arguments.
     *
     * @param name the application's name
     * @param version the application's version
     */
    public Application(final String name, final String version) {
        this.name = name;
        this.version = version;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof Application) {
            final Application other = (Application) obj;
            result = ObjectUtil.bothNullOrEqual(name, other.getName());
            result = result && ObjectUtil.bothNullOrEqual(version, other.getVersion());
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ObjectUtil.hashCode(name);
        result = prime * result + ObjectUtil.hashCode(version);
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (name != null) {
            sb.append(name);
        }
        if (version != null) {
            sb.append(", ").append(version);
        }
        return sb.toString();
    }
}