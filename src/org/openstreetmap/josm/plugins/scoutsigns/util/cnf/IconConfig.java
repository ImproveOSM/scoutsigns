/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.tools.ImageProvider;
import com.telenav.josm.common.cnf.BaseConfig;


/**
 * Utility class, holds icons and icon paths.
 *
 * @author Bea
 * @version $Revision$
 */
public final class IconConfig extends BaseConfig {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_icon.properties";

    /** The unique instance of the object */
    private static final IconConfig INSTANCE = new IconConfig();


    private final String shcName;

    private final Icon layerIcon;
    /* button panel icons */
    private final ImageIcon filterIcon;
    private final ImageIcon photoIcon;
    private final Icon tripIcon;
    private final ImageIcon commentIcon;
    private final Icon moreActionIcon;

    private final Icon backIcon;
    /* road sign status icons */
    private final ImageIcon openIcon;
    private final ImageIcon solvedIcon;
    private final ImageIcon invalidIcon;

    private final ImageIcon duplicateIcon;
    /* road sign icons & path names */
    private final ImageIcon selRoadSignBgIcon;
    private final String typesIconPath;

    private final String defaultTypeIconName;


    /* calendar icon */
    private final ImageIcon calendarIcon;


    private IconConfig() {
        super(CNF_FILE);
        shcName = readProperty("dialog.shc");
        layerIcon = getIcon("layer.icon");
        filterIcon = getIcon("filter.icon");
        photoIcon = getIcon("photo.icon");
        tripIcon = getIcon("trip.icon");
        commentIcon = getIcon("comment.icon");
        moreActionIcon = getIcon("more.icon");
        backIcon = getIcon("back.icon");
        openIcon = getIcon("status.open.icon");
        solvedIcon = getIcon("status.solved.icon");
        invalidIcon = getIcon("status.invalid.icon");
        duplicateIcon = getIcon("status.duplicate.icon");

        selRoadSignBgIcon = getIcon("sign.sel.bg");
        typesIconPath = readProperty("sign.types.path");
        defaultTypeIconName = readProperty("sign.types.def");

        calendarIcon = getIcon("calendar.icon");
    }


    public static IconConfig getInstance() {
        return INSTANCE;
    }

    public Icon getBackIcon() {
        return backIcon;
    }

    public ImageIcon getCalendarIcon() {
        return calendarIcon;
    }

    public ImageIcon getCommentIcon() {
        return commentIcon;
    }

    public String getDefaultTypeIconName() {
        return defaultTypeIconName;
    }

    public ImageIcon getDuplicateIcon() {
        return duplicateIcon;
    }

    public ImageIcon getFilterIcon() {
        return filterIcon;
    }

    public ImageIcon getInvalidIcon() {
        return invalidIcon;
    }

    public Icon getLayerIcon() {
        return layerIcon;
    }

    public Icon getMoreActionIcon() {
        return moreActionIcon;
    }

    public ImageIcon getOpenIcon() {
        return openIcon;
    }

    public ImageIcon getPhotoIcon() {
        return photoIcon;
    }

    public ImageIcon getSelRoadSignBgIcon() {
        return selRoadSignBgIcon;
    }

    public String getShcName() {
        return shcName;
    }

    public ImageIcon getSolvedIcon() {
        return solvedIcon;
    }

    public Icon getTripIcon() {
        return tripIcon;
    }

    public String getTypesIconPath() {
        return typesIconPath;
    }

    private ImageIcon getIcon(final String key) {
        return ImageProvider.get(readProperty(key));
    }
}