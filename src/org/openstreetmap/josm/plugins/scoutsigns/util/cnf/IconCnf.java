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
 * Utility class, holds icons and icon paths.
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
    
    /* button panel icons */
    private final ImageIcon filterIcon;
    private final ImageIcon photoIcon;
    private final Icon tripIcon;
    private final ImageIcon commentIcon;
    private final Icon moreActionIcon;
    
    /* road sign status icons */
    private final ImageIcon openIcon;
    private final ImageIcon solvedIcon;
    private final ImageIcon invalidIcon;
    private final ImageIcon duplicateIcon;
    
    /*  road sign icons & path names */
    private final ImageIcon selRoadSignBg;
    private final String typeIconPath;
    private final String defTypeIconName;
    
    
    private IconCnf() {
        Properties properties = CnfUtil.load(CNF_FILE);
        shcName = CnfUtil.readProperty(properties, "dialog.shc");
        layerIcon = getIcon(properties, "layer.icon");
        filterIcon = getIcon(properties, "filter.icon");
        photoIcon = getIcon(properties, "photo.icon");
        tripIcon = getIcon(properties, "trip.icon");
        commentIcon = getIcon(properties, "comment.icon");
        moreActionIcon = getIcon(properties, "more.icon");
        
        openIcon = getIcon(properties, "status.open.icon");
        solvedIcon = getIcon(properties, "status.solved.icon");
        invalidIcon =getIcon(properties, "status.invalid.icon");
        duplicateIcon = getIcon(properties, "status.duplicate.icon");

        typeIconPath = CnfUtil.readProperty(properties, "sign.types.path");
        selRoadSignBg = getIcon(properties, "sign.sel.bg");
        defTypeIconName = CnfUtil.readProperty(properties, "sign.types.def");
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
    
    public ImageIcon getFilterIcon() {
        return filterIcon;
    }
    
    public ImageIcon getPhotoIcon() {
        return photoIcon;
    }
    
    public Icon getTripIcon() {
        return tripIcon;
    }
    
    public ImageIcon getCommentIcon() {
        return commentIcon;
    }
    
    public Icon getMoreActionIcon() {
        return moreActionIcon;
    }
    
    public String getTypeIconPath() {
        return typeIconPath;
    }
    
    public ImageIcon getOpenIcon() {
        return openIcon;
    }
    
    public ImageIcon getSolvedIcon() {
        return solvedIcon;
    }
    
    public ImageIcon getInvalidIcon() {
        return invalidIcon;
    }
    
    public ImageIcon getDuplicateIcon() {
        return duplicateIcon;
    }
    
    public ImageIcon getSelRoadSignBg() {
        return selRoadSignBg;
    }
    
    public String getDefTypeIconName() {
        return defTypeIconName;
    }
}