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
 * Utility class, holds UI texts.
 *
 * @author Bea
 * @version $Revision$
 */
public final class GuiConfig extends BaseConfig {

    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_gui.properties";

    private static final GuiConfig INSTANCE = new GuiConfig();

    public static GuiConfig getInstance() {
        return INSTANCE;
    }

    private final String dlgDetailsTitle;
    /* details panel tab titles */
    private final String pnlRoadSignTitle;
    private final String pnlCarPosTitle;
    private final String pnlTripTitle;

    private final String pnlCommentsTitle;
    /* details panel texts */
    private final String lblId;
    private final String lblPoint;
    private final String lblHeight;
    private final String lblConf;
    private final String lblCreated;
    private final String lblHeading;
    private final String lblDirection;
    private final String lblForward;
    private final String lblBackward;
    private final String lblAcc;
    private final String lblMode;

    private final String lblProfile;
    /* photo frame texts */
    private final String frmPhotoTitle;
    private final String lblPhotoError;

    private final String lblPhotoMissing;
    /* filter dialog texts */
    private final String dlgFilterTitle;
    private final String lblSources;
    private final String lblTimeInt;

    private final String lblUsername;
    private final String dlgCommentTitle;

    private final String txtUsernameWarning;
    /* commonly used labels */
    private final String lblType;
    private final String lblStatus;
    private final String lblDupl;
    private final String lblApp;

    private final String lblDevice;
    /* button texts */
    private final String btnOk;
    private final String btnReset;

    private final String btnCancel;
    /* warning texts */
    private final String dlgWarningTitle;
    private final String txtDuplIdInvalid;
    private final String txtConfInvalid;
    private final String txtCommentInvalid;

    private final String txtDateInvalid;

    /* info texts */
    private final String infoClusterTitle;
    private final String infoClusterTxt;
    private final String txtMenuSolve;
    private final String txtMenuInvalid;
    private final String txtMenuDuplicate;

    private final String txtMenuReopen;
    /* action dialog titles */
    private final String dlgSolveTitle;
    private final String dlgInvalidTitle;
    private final String dlgDuplicateTitle;


    private final String dlgReopenTitle;


    private GuiConfig() {
        super(CNF_FILE);
        dlgDetailsTitle = readProperty("dialog.title");

        /* details panel tab titles */
        pnlRoadSignTitle = readProperty("details.roadSign");
        pnlCarPosTitle = readProperty("details.carPos");
        pnlTripTitle = readProperty("details.trip");
        pnlCommentsTitle = readProperty("details.comments");

        /* details panel labels */
        lblId = readProperty("details.id");
        lblPoint = readProperty("details.point");
        lblHeight = readProperty("details.height");
        lblConf = readProperty("details.conf");
        lblCreated = readProperty("details.created");
        lblHeading = readProperty("details.heading");
        lblDirection = readProperty("details.dir");
        lblForward = readProperty("details.dir.fwd");
        lblBackward = readProperty("details.dir.bwd");
        lblAcc = readProperty("details.acc");
        lblMode = readProperty("details.mode");
        lblProfile = readProperty("details.profile");

        /* photo frame texts */
        frmPhotoTitle = readProperty("photo.title");
        lblPhotoError = readProperty("photo.error");
        lblPhotoMissing = readProperty("photo.missing");

        /* filter dialog texts */
        dlgFilterTitle = readProperty("filter.title");
        lblSources = readProperty("filter.sources");
        lblTimeInt = readProperty("filter.int");
        lblUsername = readProperty("filter.username");
        dlgCommentTitle = readProperty("comment.title");
        txtUsernameWarning = readProperty("warning.missing.username");

        /* commonly used texts */
        lblType = readProperty("type");
        lblStatus = readProperty("status");
        lblDupl = readProperty("dupl");
        lblApp = readProperty("app");
        lblDevice = readProperty("device");

        /* button texts */
        btnOk = readProperty("btn.ok");
        btnReset = readProperty("btn.reset");
        btnCancel = readProperty("bnt.cancel");

        dlgWarningTitle = readProperty("warning.general.title");
        txtDuplIdInvalid = readProperty("warning.invalid.dupl");
        txtConfInvalid = readProperty("warning.invalid.conf");
        txtCommentInvalid = readProperty("warning.invalid.comment");
        txtDateInvalid = readProperty("warning.invalid.date");

        infoClusterTitle = readProperty("info.cluster.title");
        infoClusterTxt = readProperty("info.cluster.txt");

        /* menu item texts */
        txtMenuSolve = readProperty("edit.menu.solve");
        txtMenuInvalid = readProperty("edit.menu.invalid");
        txtMenuDuplicate = readProperty("edit.meuu.duplicate");
        txtMenuReopen = readProperty("edit.menu.reopen");

        dlgSolveTitle = readProperty("edit.dialog.solve");
        dlgInvalidTitle = readProperty("edit.dialog.invalid");
        dlgDuplicateTitle = readProperty("edit.dialog.duplicate");
        dlgReopenTitle = readProperty("edit.dialog.reopen");
    }

    public String getBtnCancel() {
        return btnCancel;
    }

    public String getBtnOk() {
        return btnOk;
    }

    public String getBtnReset() {
        return btnReset;
    }

    public String getDlgCommentTitle() {
        return dlgCommentTitle;
    }

    public String getDlgDetailsTitle() {
        return dlgDetailsTitle;
    }

    public String getDlgDuplicateTitle() {
        return dlgDuplicateTitle;
    }

    public String getDlgFilterTitle() {
        return dlgFilterTitle;
    }

    public String getDlgInvalidTitle() {
        return dlgInvalidTitle;
    }

    public String getDlgReopenTitle() {
        return dlgReopenTitle;
    }

    public String getDlgSolveTitle() {
        return dlgSolveTitle;
    }

    public String getDlgWarningTitle() {
        return dlgWarningTitle;
    }

    public String getFrmPhotoTitle() {
        return frmPhotoTitle;
    }

    public String getInfoClusterTitle() {
        return infoClusterTitle;
    }

    public String getInfoClusterTxt() {
        return infoClusterTxt;
    }

    public String getLblAcc() {
        return lblAcc;
    }

    public String getLblApp() {
        return lblApp;
    }

    public String getLblBackward() {
        return lblBackward;
    }

    public String getLblConf() {
        return lblConf;
    }

    public String getLblCreated() {
        return lblCreated;
    }

    public String getLblDevice() {
        return lblDevice;
    }

    public String getLblDirection() {
        return lblDirection;
    }

    public String getLblDupl() {
        return lblDupl;
    }

    public String getLblForward() {
        return lblForward;
    }

    public String getLblHeading() {
        return lblHeading;
    }

    public String getLblHeight() {
        return lblHeight;
    }

    public String getLblId() {
        return lblId;
    }

    public String getLblMode() {
        return lblMode;
    }

    public String getLblPhotoError() {
        return lblPhotoError;
    }

    public String getLblPhotoMissing() {
        return lblPhotoMissing;
    }

    public String getLblPoint() {
        return lblPoint;
    }

    public String getLblProfile() {
        return lblProfile;
    }

    public String getLblSources() {
        return lblSources;
    }

    public String getLblStatus() {
        return lblStatus;
    }

    public String getLblTimeInt() {
        return lblTimeInt;
    }

    public String getLblType() {
        return lblType;
    }

    public String getLblUsername() {
        return lblUsername;
    }

    public String getPnlCarPosTitle() {
        return pnlCarPosTitle;
    }

    public String getPnlCommentsTitle() {
        return pnlCommentsTitle;
    }

    public String getPnlRoadSignTitle() {
        return pnlRoadSignTitle;
    }

    public String getPnlTripTitle() {
        return pnlTripTitle;
    }

    public String getTxtCommentInvalid() {
        return txtCommentInvalid;
    }

    public String getTxtConfInvalid() {
        return txtConfInvalid;
    }

    public String getTxtDateInvalid() {
        return txtDateInvalid;
    }

    public String getTxtDuplIdInvalid() {
        return txtDuplIdInvalid;
    }

    public String getTxtMenuDuplicate() {
        return txtMenuDuplicate;
    }

    public String getTxtMenuInvalid() {
        return txtMenuInvalid;
    }

    public String getTxtMenuReopen() {
        return txtMenuReopen;
    }

    public String getTxtMenuSolve() {
        return txtMenuSolve;
    }

    public String getTxtUsernameWarning() {
        return txtUsernameWarning;
    }
}