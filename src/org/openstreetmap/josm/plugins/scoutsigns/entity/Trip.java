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
 * Defines the trip business entity.
 *
 * @author Beata
 * @version $Revision$
 */
public class Trip {

    private final String id;
    private final String mode;
    private final String profile;
    private final Application app;
    private final Device device;


    /**
     * Builds a new object with the given arguments.
     *
     * @param id the unique trip identifier
     * @param mode the driving mode in which the trip has been obtained
     * @param profile the profile of the routing
     * @param app the {@code Application} object, contains information related to the used application
     * @param device the {@code Device} object, contains information related to the used application
     */
    public Trip(final String id, final String mode, final String profile, final Application app, final Device device) {
        this.id = id;
        this.mode = mode;
        this.profile = profile;
        this.app = app;
        this.device = device;
    }


    public Application getApp() {
        return app;
    }

    public Device getDevice() {
        return device;
    }

    public String getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public String getProfile() {
        return profile;
    }
}