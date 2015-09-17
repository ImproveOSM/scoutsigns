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
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.List;
import java.util.Properties;


/**
 * Holds general run-time configuration properties.
 *
 * @author Bea
 * @version $Revision$
 */
public final class Config {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns.properties";

    /* default values */
    private static final int DEF_SEARCH_DELAY = 600;
    private static final int MAX_CLUSTER_ZOOM = 10;

    /** The unique instance of the object */
    private static final Config UNIQUE_INSTANCE = new Config();

    /**
     * Returns the unique instance of the {@code ServiceCnf} object
     *
     * @return a {@code ServiceCnf} object
     */
    public static Config getInstance() {
        return UNIQUE_INSTANCE;
    }

    private final String serviceUrl;
    private int searchDelay;
    private int maxClusterZoom;
    private final List<String> scoutTypes;
    private final List<String> mapillaryTypes;
    private final List<String> commonTypes;


    private Config() {
        final Properties properties = CnfUtil.load(CNF_FILE);
        serviceUrl = properties.getProperty("service.url");
        if (serviceUrl == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Missing FcdSignService url.");
        }
        try {
            searchDelay = Integer.parseInt(properties.getProperty("search.delay"));
        } catch (final NumberFormatException ex) {
            searchDelay = DEF_SEARCH_DELAY;
        }
        try {
            maxClusterZoom = Integer.parseInt(properties.getProperty("zoom.cluster.max"));
        } catch (final NumberFormatException ex) {
            maxClusterZoom = MAX_CLUSTER_ZOOM;
        }
        // scout road sign types
        scoutTypes = CnfUtil.readPropertiesList(properties, "types.scout");

        // mapillary road sign types
        mapillaryTypes = CnfUtil.readPropertiesList(properties, "types.mapillary");

        // read common types
        commonTypes = CnfUtil.readPropertiesList(properties, "types.common");
    }


    public List<String> getCommonTypes() {
        return commonTypes;
    }

    public List<String> getMapillaryTypes() {
        return mapillaryTypes;
    }

    public int getMaxClusterZoom() {
        return maxClusterZoom;
    }

    public List<String> getScoutTypes() {
        return scoutTypes;
    }

    public int getSearchDelay() {
        return searchDelay;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }
}