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
package org.openstreetmap.josm.plugins.skosigns.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.plugins.skosigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.tools.ImageProvider;


/**
 * Factory for the road sign types.
 * 
 * @author Beata
 * @version $Revision$
 */
public final class TypeIconFactory {
    
    private static Map<String, ImageIcon> map;
    
    /* the icons path */
    private static final String PATH = "types/";
    
    /* the icons extension */
    private static final String EXT = ".png";
    
    /* default icon to be used if no corresponding icon found*/
    private static final String DF_ICON_PATH = "types/UNKNOWN.png";
    private static ImageIcon defaultIcon;
    
    
    private static final TypeIconFactory UNIQUE_INSTANCE =
            new TypeIconFactory();
    
    
    private TypeIconFactory() {
        map = new HashMap<>();
        defaultIcon = ImageProvider.get(DF_ICON_PATH);
        List<String> types = ServiceCnf.getInstance().getTypes();
        for (String type : types) {
            ImageIcon icon = ImageProvider.get(PATH + type + EXT);
            if (icon == null) {
                icon = defaultIcon;
            }
            map.put(type, icon);
        }
    }
    
    
    /**
     * Returns the unique instance of the {@code TypeIconFactory}.
     * 
     * @return a {@code TypeIconFactory}
     */
    public static TypeIconFactory getInstance() {
        return UNIQUE_INSTANCE;
    }
    
    /**
     * Returns the icon corresponding to the given type. The method returns
     * a default icon if no corresponding icon found.
     * 
     * @param type specifies a road sign type
     * @return an {@code Icon}
     */
    public ImageIcon getIcon(String type) {
        ImageIcon icon = map.get(type);
        return icon != null ? icon : defaultIcon;
    }
}