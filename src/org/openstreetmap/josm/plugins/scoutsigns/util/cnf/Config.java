/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.List;
import com.telenav.josm.common.cnf.BaseConfig;


/**
 * Holds general run-time configuration properties.
 *
 * @author Bea
 * @version $Revision$
 */
public final class Config extends BaseConfig {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns.properties";

    /* default values */
    private static final int DEF_SEARCH_DELAY = 600;
    private static final int MAX_CLUSTER_ZOOM = 10;

    /** The unique instance of the object */
    private static final Config UNIQUE_INSTANCE = new Config();


    private final String serviceUrl;
    private int searchDelay;
    private int maxClusterZoom;
    private final List<String> signTypes;


    private Config() {
        super(CNF_FILE);
        serviceUrl = readProperty("service.url");
        if (serviceUrl == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Missing FcdSignService url.");
        }
        try {
            searchDelay = Integer.parseInt(readProperty("search.delay"));
        } catch (final NumberFormatException ex) {
            searchDelay = DEF_SEARCH_DELAY;
        }
        try {
            maxClusterZoom = Integer.parseInt(readProperty("zoom.cluster.max"));
        } catch (final NumberFormatException ex) {
            maxClusterZoom = MAX_CLUSTER_ZOOM;
        }
        // scout road sign types
        signTypes = readPropertiesList("types");
    }


    /**
     * Returns the unique instance of the {@code ServiceCnf} object
     *
     * @return a {@code ServiceCnf} object
     */
    public static Config getInstance() {
        return UNIQUE_INSTANCE;
    }

    public int getMaxClusterZoom() {
        return maxClusterZoom;
    }

    public int getSearchDelay() {
        return searchDelay;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public List<String> getSignTypes() {
        return signTypes;
    }
}