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