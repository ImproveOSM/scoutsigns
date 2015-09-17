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
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Holds road sign types based on the existing sources.
 *
 * @author Beata
 * @version $Revision$
 */
public final class TypeConfig {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_type.properties";

    /** The unique instance of the object */
    private static final TypeConfig UNIQUE_INSTANCE = new TypeConfig();

    public static TypeConfig getInstance() {
        return UNIQUE_INSTANCE;
    }

    private final List<String> scoutTypes;
    private final List<String> mapillaryTypes;
    private final List<String> commonTypes;
    private final Map<String, String> translationMap;


    private TypeConfig() {
        final Properties properties = CnfUtil.load(CNF_FILE);
        // scout road sign types
        scoutTypes = CnfUtil.readPropertiesList(properties, "types.scout");

        // mapillary road sign types
        mapillaryTypes = CnfUtil.readPropertiesList(properties, "types.mapillary");

        // read common types
        commonTypes = CnfUtil.readPropertiesList(properties, "types.common");

        translationMap = new HashMap<>();
        for (final Object key : properties.keySet()) {
            final String keyStr = (String) key;
            if (keyStr.startsWith("tr_")) {
                final String mapKey = keyStr.replace("tr_", "");
                translationMap.put(mapKey, CnfUtil.readProperty(properties, keyStr));
            }
        }
    }


    public List<String> getCommonTypes() {
        return commonTypes;
    }

    /**
     * Returns the Mapillary road sign type corresponding to the given SCOUT type.
     *
     * @param type a SCOUT type
     * @return the Mapillary type
     */
    public String getMapillaryType(final String scoutType) {
        final String value = translationMap.get(scoutType);
        return value != null ? value : scoutType;
    }

    public List<String> getMapillaryTypes() {
        return mapillaryTypes;
    }

    public List<String> getScoutTypes() {
        return scoutTypes;
    }
}