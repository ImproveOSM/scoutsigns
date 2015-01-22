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


/**
 * Holds road sign cluster icons.
 * 
 * @author Beata
 * @version $Revision$
 */
public final class ClusterIconCnf {
    
    private static final String CNF_FILE = "scoutsigns_cluster_icon.properties";
    
    private static final ClusterIconCnf INSTANCE = new ClusterIconCnf();
    
    private final ImageIcon defIcon;
    
    private final Map<Integer, ImageIcon> map;
    
    
    private ClusterIconCnf() {
        Properties properties = CnfUtil.load(CNF_FILE);
        defIcon = ImageProvider.get(properties.getProperty("default"));
        properties.remove("default");
        map = new TreeMap<Integer, ImageIcon>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            ImageIcon icon = new ImageProvider((String) entry.getValue()).get();
            map.put(new Integer((String) entry.getKey()), icon);
        }
    }
    
    
    /**
     * Returns the instance of the {@code ClusterIconCnf}
     * 
     * @return a {@code ClusterIconCnf}
     */
    public static ClusterIconCnf getInstance() {
        return INSTANCE;
    }
    
    /**
     * Returns the icon corresponding to the given road sign count.
     * 
     * @param count a road sign count
     * @return an {@code ImageIcon} object
     */
    public ImageIcon getIcon(Integer count) {
        ImageIcon icon = null;
        for (Integer key : map.keySet()) {
            if (count < key) {
                icon = map.get(key);
                break;
            }
        }
        return icon != null ? icon : defIcon;
    }
}