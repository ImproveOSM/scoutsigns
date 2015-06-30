/*
 * Copyright (c) 2014 SKOBBLER SRL.
 * Cuza Voda 1, Cluj-Napoca, Cluj, 400107, Romania
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with SKOBBLER SRL.
 *
 * Created on Jul 29, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.List;
import java.util.Properties;


/**
 * Utility class holds run-time configuration properties.
 *
 * @author Bea
 * @version $Revision$
 */
public final class ServiceCnf {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns.properties";

    /* default values */
    private static final int DEF_SEARCH_DELAY = 600;
    private static final int MAX_CLUSTER_ZOOM = 10;

    /** The unique instance of the object */
    private static final ServiceCnf UNIQUE_INSTANCE = new ServiceCnf();

    /**
     * Returns the unique instance of the {@code ServiceCnf} object
     *
     * @return a {@code ServiceCnf} object
     */
    public static ServiceCnf getInstance() {
        return UNIQUE_INSTANCE;
    }

    private final String serviceUrl;
    private final String mapillaryImageUrl;
    private final String mapillaryImagePage;


    private int searchDelay;
    private int maxClusterZoom;
    private final List<String> scoutTypes;

    private final List<String> mapillaryTypes;


    private final List<String> commonTypes;


    private ServiceCnf() {
        final Properties properties = CnfUtil.load(CNF_FILE);
        serviceUrl = properties.getProperty("service.url");
        if (serviceUrl == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Missing FcdSignService url.");
        }
        mapillaryImageUrl = properties.getProperty("mapillary.image.url");
        if (mapillaryImageUrl == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Missing Mapillary image url.");
        }
        mapillaryImagePage = properties.getProperty("mapillary.image.page");
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

    public String getMapillaryImagePage() {
        return mapillaryImagePage;
    }

    public String getMapillaryImageUrl() {
        return mapillaryImageUrl;
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

    public void setSearchDelay(final int searchDelay) {
        this.searchDelay = searchDelay;
    }
}