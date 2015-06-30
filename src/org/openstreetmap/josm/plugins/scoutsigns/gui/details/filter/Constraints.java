/*
 * Copyright (c) 2015, skobbler GmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Jun 8, 2015 by Beata
 * Modified on $DateTime$ by $Author$
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

    static final GridBagConstraints LBL_SOURCES = new GridBagConstraints(0, 0, 1, 1, 1, 1,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(7, 5, 3, 5), 0, 0);
    static final GridBagConstraints PNL_SOURCES = new GridBagConstraints(1, 0, 1, 1, 1, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 3, 5), 0, 0);
    static final GridBagConstraints CBB_SCOUT = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
    static final GridBagConstraints CBB_MAPILLARY = new GridBagConstraints(1, 0, 1, 1, 0, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 0, 0);
    static final GridBagConstraints LBL_STATUS = new GridBagConstraints(0, 1, 1, 1, 1, 1,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 3, 5), 0, 0);
    static final GridBagConstraints PNL_STATUS = new GridBagConstraints(1, 1, 2, 1, 1, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 2, 3), 0, 0);
    static final GridBagConstraints LBL_TYPE = new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LIST_TYPE = new GridBagConstraints(1, 2, 2, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 94);
    static final GridBagConstraints LBL_INT = new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(7, 5, 3, 5), 0, 0);
    static final GridBagConstraints CBB_START = new GridBagConstraints(1, 3, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 3, 5), 0, 0);
    static final GridBagConstraints CBB_END = new GridBagConstraints(2, 3, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_DUPL = new GridBagConstraints(0, 4, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_DUPL = new GridBagConstraints(1, 4, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_CONF = new GridBagConstraints(0, 5, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_CONF = new GridBagConstraints(1, 5, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_USERNAME = new GridBagConstraints(0, 6, 1, 1, 1, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_USERNAME = new GridBagConstraints(1, 6, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_DEV = new GridBagConstraints(0, 7, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_OS_NAME = new GridBagConstraints(1, 7, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_OS_VERS = new GridBagConstraints(2, 7, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints LBL_APP = new GridBagConstraints(0, 8, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_APP_NAME = new GridBagConstraints(1, 8, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);
    static final GridBagConstraints TXT_APP_VERS = new GridBagConstraints(2, 8, 1, 1, 1, 0, GridBagConstraints.CENTER,
            GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5), 0, 0);

    private Constraints() {}
}