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
package org.openstreetmap.josm.plugins.scoutsigns.gui.details;

import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import com.telenav.josm.common.gui.BasicInfoPanel;


/**
 * Defines an abstract {@code JPanel} template for displaying basic information in (information name, value) format
 * object of type T.
 *
 * @author Bea
 * @version $Revision$
 */
abstract class InfoPanel<T> extends BasicInfoPanel<T> {

    private static final long serialVersionUID = 2835853554189232179L;

    /* constants used for computing GUI component dimensions */
    protected static final int RECT_Y = 0;

    /** holds GUI texts */
    private static GuiConfig guiCnf = GuiConfig.getInstance();

    static GuiConfig getGuiCnf() {
        return guiCnf;
    }

    /**
     * Builds a new {@code InfoPanel} with the given argument.
     */
    InfoPanel() {
        super();
        updateData(null);
    }
}