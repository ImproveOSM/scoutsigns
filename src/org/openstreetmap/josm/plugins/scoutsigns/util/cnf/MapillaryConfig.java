/*
 * Copyright (c) 2015 SKOBBLER SRL.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with SKOBBLER SRL.
 *
 * Created on Mar 11, 2015 by Beata
 * Modified on $Date$
 *          by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.Properties;


/**
 * Holds configuration constants accessing the Mapillary API.
 *
 * @author Beata
 * @version $Revision$
 */
public final class MapillaryConfig {

    private static final int DEF_LIMIT = 500;

    private static final String CNF_FILE = "scoutsigns_mapillary.properties";

    private static final MapillaryConfig INSTANCE = new MapillaryConfig();

    /**
     * Returns the only instance of this singleton object.
     *
     * @return the instance of the {@code Configuration}
     */
    public static MapillaryConfig getInstance() {
        return INSTANCE;
    }

    private String apiUrl = "";
    private String apiKey = "";
    private String imageUrl = "";
    private String imagePag = "";
    private int limit;


    private MapillaryConfig() {
        final Properties properties = CnfUtil.load(CNF_FILE);
        apiUrl = properties.getProperty("api.url");
        if (apiUrl == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Missing Mapillary API url.");
        }
        apiKey = properties.getProperty("api.key");
        if (apiKey == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Missing Mapillary API key.");
        }
        imageUrl = properties.getProperty("image.url");
        if (imageUrl == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Missing Mapillary image url.");
        }
        imagePag = properties.getProperty("image.page");
        try {
            limit = Integer.parseInt(properties.getProperty("limit"));
        } catch (final NumberFormatException ex) {
            limit = DEF_LIMIT;
        }
    }


    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getImagePag() {
        return imagePag;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLimit() {
        return limit;
    }
}