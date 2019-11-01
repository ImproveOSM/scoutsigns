/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
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