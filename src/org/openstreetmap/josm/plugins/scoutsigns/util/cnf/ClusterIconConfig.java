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

import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.tools.ImageProvider;
import org.openstreetmap.josm.tools.Pair;
import com.telenav.josm.common.cnf.BaseConfig;


/**
 * Holds road sign cluster icon properties.
 *
 * @author Beata
 * @version $Revision$
 */
public final class ClusterIconConfig extends BaseConfig {

    private static final String CNF_FILE = "scoutsigns_cluster_icon.properties";

    private static final ClusterIconConfig INSTANCE = new ClusterIconConfig();

    /**
     * Returns the instance of the {@code ClusterIconCnf}
     *
     * @return a {@code ClusterIconCnf}
     */
    public static ClusterIconConfig getInstance() {
        return INSTANCE;
    }

    private final Pair<ImageIcon, Float> def;
    private final Map<Integer, Pair<ImageIcon, Float>> map;


    private ClusterIconConfig() {
        super(CNF_FILE);
        def = buildPair(readProperty("default"));
        map = new TreeMap<>();
        for (final Object key : keySet()) {
            final Pair<ImageIcon, Float> pair = buildPair(readProperty(key.toString()));
            if (!key.toString().equals("default")) {
                map.put(new Integer((String) key), pair);
            }
        }
    }

    /**
     * Returns the icon and transparency corresponding to the given road sign count.
     *
     * @param count a road sign count
     * @return an {@code ImageIcon}, {@code Float} pair
     */
    public Pair<ImageIcon, Float> getIcon(final Integer count) {
        Pair<ImageIcon, Float> value = null;
        for (final Integer key : map.keySet()) {
            if (count < key) {
                value = map.get(key);
                break;
            }
        }
        return value != null ? value : def;
    }

    private Pair<ImageIcon, Float> buildPair(final String value) {
        final String[] values = value.split(";");
        final ImageIcon icon = new ImageProvider(values[0]).get();
        final Float transparency = new Float(values[1]);
        return new Pair<ImageIcon, Float>(icon, transparency);
    }
}