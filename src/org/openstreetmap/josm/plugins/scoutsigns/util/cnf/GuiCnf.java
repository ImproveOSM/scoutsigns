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


/**
 * Utility class, holds UI texts.
 * 
 * @author Bea
 * @version $Revision$
 */
public final class GuiCnf {
    
    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_gui.properties";
    
    private static final GuiCnf INSTANCE = new GuiCnf();
    
    private final String dlgTitle;
    
    /* details panel tab titles */
    private final String roadSignTitle;
    private final String carPosTitle;
    private final String tripTitle;
    private final String commentsTitle;
    
    /* details panel texts */
    private final String idLbl;
    private final String pointLbl;
    private final String heightLbl;
    private final String confLbl;
    private final String createdLbl;
    private final String headingLbl;
    private final String accLbl;
    private final String modeLbl;
    private final String profileLbl;
    
    /* photo frame texts */
    private final String photoTitle;
    private final String photoErrorLbl;
    private final String photoMissingLbl;
    
    /* filter dialog texts */
    private final String filterTitle;
    private final String timeIntLbl;
    
    /* commonly used labels */
    private final String typeLbl;
    private final String statusLbl;
    private final String duplLbl;
    private final String appLbl;
    private final String deviceLbl;
    
    
    /* button texts */
    private final String btnOk;
    private final String btnReset;
    private final String btnCancel;
    
    
    private GuiCnf() {
        Properties properties = CnfUtil.load(CNF_FILE);
        dlgTitle = CnfUtil.readProperty(properties, "dialog.title");
        
        /* details panel tab titles */
        roadSignTitle = CnfUtil.readProperty(properties, "details.roadSign");
        carPosTitle = CnfUtil.readProperty(properties, "details.carPos");
        tripTitle = CnfUtil.readProperty(properties, "details.trip");
        commentsTitle = CnfUtil.readProperty(properties, "details.comments");
        
        /* details panel labels */
        idLbl = CnfUtil.readProperty(properties, "details.id");
        pointLbl = CnfUtil.readProperty(properties, "details.point");
        heightLbl = CnfUtil.readProperty(properties, "details.height");
        confLbl = CnfUtil.readProperty(properties, "details.conf");
        createdLbl = CnfUtil.readProperty(properties, "details.created");
        headingLbl = CnfUtil.readProperty(properties, "details.heading");
        accLbl = CnfUtil.readProperty(properties, "details.acc");
        modeLbl = CnfUtil.readProperty(properties, "details.mode");
        profileLbl = CnfUtil.readProperty(properties, "details.profile");
        
        /* photo frame texts */
        photoTitle = CnfUtil.readProperty(properties, "photo.title");
        photoErrorLbl = CnfUtil.readProperty(properties, "photo.error");
        photoMissingLbl = CnfUtil.readProperty(properties, "photo.missing");
        
        /* filter dialog texts */
        filterTitle = CnfUtil.readProperty(properties, "filter.title");
        timeIntLbl = CnfUtil.readProperty(properties, "filter.int");
        
        /* commonly used texts */
        typeLbl = CnfUtil.readProperty(properties, "type");
        statusLbl = CnfUtil.readProperty(properties, "status");
        duplLbl = CnfUtil.readProperty(properties, "dupl");
        appLbl = CnfUtil.readProperty(properties, "app");
        deviceLbl = CnfUtil.readProperty(properties, "device");
        
        /* button texts */
        btnOk = CnfUtil.readProperty(properties, "btn.ok");
        btnReset = CnfUtil.readProperty(properties, "btn.reset");
        btnCancel = CnfUtil.readProperty(properties, "bnt.cancel");
    }
    
    
    public static GuiCnf getInstance() {
        return INSTANCE;
    }
    
    public String getDlgTitle() {
        return dlgTitle;
    }
    
    public String getRoadSignTitle() {
        return roadSignTitle;
    }
    
    public String getCarPosTitle() {
        return carPosTitle;
    }
    
    public String getTripTitle() {
        return tripTitle;
    }
    
    public String getCommentsTitle() {
        return commentsTitle;
    }
    
    public String getIdLbl() {
        return idLbl;
    }
    
    public String getPointLbl() {
        return pointLbl;
    }
    
    public String getHeightLbl() {
        return heightLbl;
    }
    
    public String getConfLbl() {
        return confLbl;
    }
    
    public String getCreatedLbl() {
        return createdLbl;
    }
    
    public String getHeadingLbl() {
        return headingLbl;
    }
    
    public String getAccLbl() {
        return accLbl;
    }
    
    public String getModeLbl() {
        return modeLbl;
    }
    
    public String getProfileLbl() {
        return profileLbl;
    }
    
    public String getPhotoTitle() {
        return photoTitle;
    }
    
    public String getPhotoErrorLbl() {
        return photoErrorLbl;
    }
    
    public String getPhotoMissingLbl() {
        return photoMissingLbl;
    }
    
    public String getFilterTitle() {
        return filterTitle;
    }
    
    public String getTimeIntLbl() {
        return timeIntLbl;
    }
    
    public String getTypeLbl() {
        return typeLbl;
    }
    
    public String getStatusLbl() {
        return statusLbl;
    }
    
    public String getDuplLbl() {
        return duplLbl;
    }
    
    public String getAppLbl() {
        return appLbl;
    }
    
    public String getDeviceLbl() {
        return deviceLbl;
    }
    
    public String getBtnOk() {
        return btnOk;
    }
    
    public String getBtnReset() {
        return btnReset;
    }
    
    public String getBtnCancel() {
        return btnCancel;
    }
}