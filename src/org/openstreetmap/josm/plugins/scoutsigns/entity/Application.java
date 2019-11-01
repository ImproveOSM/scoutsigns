/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.entity;

import com.telenav.josm.common.entity.EntityUtil;


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
            result = EntityUtil.bothNullOrEqual(name, other.getName());
            result = result && EntityUtil.bothNullOrEqual(version, other.getVersion());
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
        result = prime * result + EntityUtil.hashCode(name);
        result = prime * result + EntityUtil.hashCode(version);
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