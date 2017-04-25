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
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
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

    private final ImageIcon defaultIcon;
    private final Map<String, ImageIcon> map;


    private TypeIconFactory() {
        final Config serviceCnf = Config.getInstance();

        map = new LinkedHashMap<>();
        for (final String type : serviceCnf.getSignTypes()) {
            final ImageIcon icon = loadIcon(type);
            if (icon != null) {
                map.put(type, icon);
            }
        }

        defaultIcon = loadIcon(IconConfig.getInstance().getDefaultTypeIconName());
    }

    /**
     * Returns the icon corresponding to the given type and source. The method returns a default icon, if no icon
     * corresponds to the given arguments.
     *
     * @param type specifies the road sign's type
     * @return a {@code ImageIcon} object
     */
    public ImageIcon getIcon(final String type) {
        ImageIcon icon;
        icon = map.get(type);
        if (icon == null) {
            icon = defaultIcon;
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
    private ImageIcon loadIcon(final String type) {
        ImageIcon icon;
        final IconConfig iconCnf = IconConfig.getInstance();
        try {
            icon = ImageProvider.get(iconCnf.getTypesIconPath() + "" + type + EXT);
        } catch (final RuntimeException e) {
            icon = null;
        }
        return icon;
    }
}