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
 * Created on Jan 21, 2015 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.tools.ImageProvider;
import org.openstreetmap.josm.tools.Pair;


/**
 * Holds road sign cluster icon properties.
 *
 * @author Beata
 * @version $Revision$
 */
public final class ClusterIconCnf {

    private static final String CNF_FILE = "scoutsigns_cluster_icon.properties";

    private static final ClusterIconCnf INSTANCE = new ClusterIconCnf();

    /**
     * Returns the instance of the {@code ClusterIconCnf}
     *
     * @return a {@code ClusterIconCnf}
     */
    public static ClusterIconCnf getInstance() {
        return INSTANCE;
    }

    private final Pair<ImageIcon, Float> def;


    private final Map<Integer, Pair<ImageIcon, Float>> map;

    private ClusterIconCnf() {
        final Properties properties = CnfUtil.load(CNF_FILE);
        def = buildPair(properties.getProperty("default"));
        properties.remove("default");
        map = new TreeMap<>();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final Pair<ImageIcon, Float> pair = buildPair((String) entry.getValue());
            map.put(new Integer((String) entry.getKey()), pair);
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