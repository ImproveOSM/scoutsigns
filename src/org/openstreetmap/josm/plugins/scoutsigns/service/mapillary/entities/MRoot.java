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
 * The object returned as a response from the Mapillary API.
 *
 * @author Beata
 * @version $Revision$
 */
public class MRoot {

    /* error response */
    private String code;
    private String message;

    /* search response */
    private Collection<MImage> ims;


    /**
     * Builds a new object with the given arguments.
     *
     * @param ims a collection of road sign images
     */
    public MRoot(final Collection<MImage> ims) {
        this.ims = ims;
    }

    /**
     * Builds a new object with the given arguments.
     *
     * @param code the error code
     * @param message the error message
     */
    public MRoot(final String code, final String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public Collection<MImage> getIms() {
        return ims;
    }

    public String getMessage() {
        return message;
    }
}