/*
 * Copyright (c) 2014, skobbler GmbH
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
 * Created on Jul 30, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Source;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.tools.ImageProvider;


/**
 * Factory for the road sign types icons.
 *
 * @author Beata
 * @version $Revision$
 */
public final class TypeIconFactory {

    /* the icons extension */
    private static final String EXT = ".png";

    private static final TypeIconFactory UNIQUE_INSTANCE = new TypeIconFactory();

    /**
     * Returns the unique instance of the {@code TypeIconFactory}.
     *
     * @return a {@code TypeIconFactory}
     */
    public static TypeIconFactory getInstance() {
        return UNIQUE_INSTANCE;
    }

    private final ImageIcon defaultScoutType;
    private final ImageIcon defaultMapillaryType;
    private final Map<String, ImageIcon> scoutSignTypes;


    private final Map<String, ImageIcon> mapillarySignTypes;


    private final Map<String, ImageIcon> commonSignTypes;


    private TypeIconFactory() {
        final ServiceCnf serviceCnf = ServiceCnf.getInstance();

        scoutSignTypes = new LinkedHashMap<String, ImageIcon>();
        for (final String type : serviceCnf.getScoutTypes()) {
            final ImageIcon icon = loadIcon(Source.SCOUT, type);
            if (icon != null) {
                scoutSignTypes.put(type, icon);
            }
        }

        mapillarySignTypes = new LinkedHashMap<String, ImageIcon>();
        for (final String type : serviceCnf.getMapillaryTypes()) {
            final ImageIcon icon = loadIcon(Source.MAPILLARY, type);
            if (icon != null) {
                mapillarySignTypes.put(type, icon);
            }
        }

        // use same icons as for scout road signs
        commonSignTypes = new LinkedHashMap<String, ImageIcon>();
        for (final String type : serviceCnf.getCommonTypes()) {
            final ImageIcon icon = getIcon(Source.SCOUT, type);
            if (icon != null) {
                commonSignTypes.put(type, icon);
            }
        }
        defaultScoutType = loadIcon(Source.SCOUT, IconCnf.getInstance().getDefaultTypeIconName());
        defaultMapillaryType = loadIcon(Source.MAPILLARY, IconCnf.getInstance().getDefaultTypeIconName());
    }

    /**
     * Returns the icon corresponding to the given type and source. The method returns a default icon, if no icon
     * corresponds to the given arguments.
     *
     * @param source specifies the road sign source
     * @param type specifies the road sign's type
     * @return a {@code ImageIcon} object
     */
    public ImageIcon getIcon(final Source source, final String type) {
        ImageIcon icon;
        if (source == null) {
            icon = commonSignTypes.get(type);
            if (icon == null) {
                icon = defaultScoutType;
            }
        } else if (source == Source.MAPILLARY) {
            icon = mapillarySignTypes.get(type);
            if (icon == null) {
                icon = defaultMapillaryType;
            }
        } else {
            icon = scoutSignTypes.get(type);
            if (icon == null) {
                icon = defaultScoutType;
            }
        }
        return icon;
    }

    /**
     * Returns the icon corresponding to the given type. The method returns a default icon, if no icon corresponds to
     * the given type.
     *
     * @param type specifies a road sign type
     * @return an {@code ImageIcon} object
     */
    private ImageIcon loadIcon(final Source source, final String type) {
        ImageIcon icon;
        final IconCnf iconCnf = IconCnf.getInstance();
        final String iconPath =
                source == Source.MAPILLARY ? iconCnf.getMapillaryTypesIconPath() : iconCnf.getScoutTypesIconPath();
        try {
            icon = ImageProvider.get(iconPath + "" + type + EXT);
        } catch (final RuntimeException e) {
            icon = null;
        }
        return icon;
    }
}