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
package org.openstreetmap.josm.plugins.scoutsigns.argument;


/**
 * Timestamp filter for the search road sign method. This filter defines an
 * interval of time in which the returned road signs have been created. If any
 * filed of this class is null then it will not be considered as a search
 * criteria.
 * 
 * @author Bea
 * @version $Revision$
 */
public class TimestampFilter {
    
    private Long from;
    private Long to;
    
    
    /**
     * Builds a new {@code TimestampFilter} with the given arguments.
     * 
     * @param from the start timestamp in 'Unix time' format
     * @param to the end timestamp in 'Unix time' format
     */
    public TimestampFilter(Long from, Long to) {
        this.from = from;
        this.to = to;
    }
    
    
    public Long getFrom() {
        return from;
    }
    
    public Long getTo() {
        return to;
    }
}