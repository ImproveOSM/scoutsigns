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
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.GridBagConstraints;
import java.awt.Insets;


/**
 *
 *
 * @author Beata
 */
final class Constraints {

    static final GridBagConstraints LBL_STATUS = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 3, 5), 0, 0);
    static final GridBagConstraints PNL_STATUS = new GridBagConstraints(1, 0, 2, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 2, 3), 0, 0);
    static final GridBagConstraints LBL_TYPE = new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LIST_TYPE = new GridBagConstraints(1, 1, 2, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 94);
    static final GridBagConstraints LBL_INT = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(7, 5, 3, 5), 0, 0);
    static final GridBagConstraints CBB_START = new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 3, 5), 0, 0);
    static final GridBagConstraints CBB_END = new GridBagConstraints(2, 2, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_DUPL = new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_DUPL = new GridBagConstraints(1, 3, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_CONF = new GridBagConstraints(0, 4, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_CONF = new GridBagConstraints(1, 4, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_USERNAME = new GridBagConstraints(0, 5, 1, 1, 1, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_USERNAME = new GridBagConstraints(1, 5, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_DEV = new GridBagConstraints(0, 6, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_OS_NAME = new GridBagConstraints(1, 6, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_OS_VERS = new GridBagConstraints(2, 6, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_APP = new GridBagConstraints(0, 7, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_APP_NAME = new GridBagConstraints(1, 7, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_APP_VERS = new GridBagConstraints(2, 7, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);

    private Constraints() {}
}