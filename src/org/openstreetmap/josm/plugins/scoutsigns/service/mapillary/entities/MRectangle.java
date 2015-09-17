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


/**
 * Defines the attributes of a matched object (rectangle).
 *
 * @author Beata
 * @version $Revision$
 */
public class MRectangle {

    private final Double score;
    private final String type;


    /**
     * Builds a new object with the given arguments.
     *
     * @param score represents the confidence score of match
     * @param type represents the road sign type
     */
    public MRectangle(final Double score, final String type) {
        this.score = score;
        this.type = type;
    }


    public Double getScore() {
        return score;
    }

    public String getType() {
        return type;
    }
}