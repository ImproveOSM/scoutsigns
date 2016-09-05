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

    public static TltConfig getInstance() {
        return INSTANCE;
    }

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