/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.tools.ImageProvider;
import com.telenav.josm.common.cnf.BaseConfig;
import com.telenav.josm.common.entity.Pair;


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
        return new Pair<>(icon, transparency);
    }
}