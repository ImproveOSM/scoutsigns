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
package org.openstreetmap.josm.plugins.scoutsigns.service.mapillary.entities;

import java.util.Collection;


/**
 * Defines the attributes of an image.
 *
 * @author Beata
 * @version $Revision$
 */
public class MImage {

    private final String key;
    private final Double lat;
    private final Double lon;
    private final Collection<MRectangle> rects;


    /**
     * Builds a new object with the given arguments.
     *
     * @param key the key of the image
     * @param lat the latitude of the image
     * @param lon the longitude of the image
     * @param rects a collection of matched objects
     */
    public MImage(final String key, final Double lat, final Double lon, final Collection<MRectangle> rects) {
        this.key = key;
        this.lat = lat;
        this.lon = lon;
        this.rects = rects;
    }


    public String getKey() {
        return key;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public Collection<MRectangle> getRects() {
        return rects;
    }
}