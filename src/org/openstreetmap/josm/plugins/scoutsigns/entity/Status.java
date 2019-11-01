/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
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