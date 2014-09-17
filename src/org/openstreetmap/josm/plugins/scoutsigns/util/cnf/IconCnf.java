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
 * Created on Jun 17, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.util.Properties;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.tools.ImageProvider;


/**
 * 
 * @author Bea
 * @version $Revision$
 */
public final class IconCnf {
    
    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_icon.properties";
    
    /** The unique instance of the object */
    private static final IconCnf INSTANCE = new IconCnf();
    
    private final String shcName;
    private final Icon layerIcon;
    private final Icon filterIcon;
    private final ImageIcon photoIcon;
    private final Icon tripIcon;
    private final Icon commentIcon;
    private final Icon moreActionIcon;
    private final String typeIconPath;
    
    
    private IconCnf() {
        Properties properties = CnfUtil.load(CNF_FILE);
        shcName = CnfUtil.readProperty(properties, "dialog.shc");
        layerIcon = getIcon(properties, "layer.icon");
        filterIcon = getIcon(properties, "filter.icon");
        photoIcon = getIcon(properties, "photo.icon");
        tripIcon = getIcon(properties, "trip.icon");
        commentIcon = getIcon(properties, "comment.icon");
        moreActionIcon = getIcon(properties, "more.icon");
        typeIconPath = CnfUtil.readProperty(properties, "types.path");
    }
    
    
    private ImageIcon getIcon(Properties properties, String key) {
        return ImageProvider.get(CnfUtil.readProperty(properties, key));
    }
    
    public static IconCnf getInstance() {
        return INSTANCE;
    }
    
    public String getShcName() {
        return shcName;
    }
    
    public Icon getLayerIcon() {
        return layerIcon;
    }
    
    public Icon getFilterIcon() {
        return filterIcon;
    }
    
    public ImageIcon getPhotoIcon() {
        return photoIcon;
    }
    
    public Icon getTripIcon() {
        return tripIcon;
    }
    
    public Icon getCommentIcon() {
        return commentIcon;
    }
    
    public Icon getMoreActionIcon() {
        return moreActionIcon;
    }
    
    public String getTypeIconPath() {
        return typeIconPath;
    }
}