/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import com.telenav.josm.common.cnf.BaseConfig;


/**
 * Utility class, holds UI tool tip texts.
 *
 * @author Bea
 * @version $Revision$
 */
public final class TltConfig extends BaseConfig {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_tlt.properties";

    private static final TltConfig INSTANCE = new TltConfig();

    private final String pluginTlt;

    private final String layerInfo;
    /* button panel tool tips */
    private final String btnFilter;
    private final String btnPhoto;
    private final String btnTrip;
    private final String btnComment;
    private final String btnMoreAction;
    private final String btnBack;
    private final String btnInvalid;
    private final String btnSolved;
    private final String btnDuplicate;


    private final String btnOpen;


    private TltConfig() {
        super(CNF_FILE);
        pluginTlt = readProperty("plugin.tlt");
        layerInfo = readProperty("layer.info");
        btnFilter = readProperty("btn.filter");
        btnPhoto = readProperty("btn.photo");
        btnTrip = readProperty("btn.trip");
        btnComment = readProperty("btn.comment");
        btnMoreAction = readProperty("btn.more");
        btnBack = readProperty("btn.back");
        btnInvalid = readProperty("btn.invalid");
        btnSolved = readProperty("btn.solved");
        btnDuplicate = readProperty("btn.duplicate");
        btnOpen = readProperty("btn.open");
    }


    public static TltConfig getInstance() {
        return INSTANCE;
    }

    public String getBtnBack() {
        return btnBack;
    }

    public String getBtnComment() {
        return btnComment;
    }

    public String getBtnDuplicate() {
        return btnDuplicate;
    }

    public String getBtnFilter() {
        return btnFilter;
    }

    public String getBtnInvalid() {
        return btnInvalid;
    }

    public String getBtnMoreAction() {
        return btnMoreAction;
    }

    public String getBtnOpen() {
        return btnOpen;
    }

    public String getBtnPhoto() {
        return btnPhoto;
    }

    public String getBtnSolved() {
        return btnSolved;
    }

    public String getBtnTrip() {
        return btnTrip;
    }

    public String getLayerInfo() {
        return layerInfo;
    }

    public String getPluginTlt() {
        return pluginTlt;
    }
}