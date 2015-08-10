package org.openstreetmap.josm.plugins.scoutsigns.entity;

import java.util.Arrays;
import java.util.List;


/**
 * Defines the road sign status entity.
 *
 * @author Beata
 * @version $Revision$
 */
public enum Status {

    OPEN, SOLVED, DUPLICATE, INVALID;

    public static final List<Status> VALUES_LIST = Arrays.asList(values());
}