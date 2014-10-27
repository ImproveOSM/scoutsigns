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
 * Utility class, holds UI tool tip texts.
 * 
 * @author Bea
 * @version $Revision$
 */
public final class TltCnf {
    
    /** The name of the configuration file */
    private static final String CNF_FILE = "scoutsigns_tlt.properties";
    
    private static final TltCnf INSTANCE = new TltCnf();
    
    private final String pluginTlt;
    private final String layerInfo;
    
    /* calendar tool tips */
    private final String prevMonth;
    private final String nextMonth;
    
    /* button panel tool tips */
    private final String btnFilter;
    private final String btnPhoto;
    private final String btnTrip;
    private final String btnComment;
    private final String btnMoreAction;
    private final String btnInvalid;
    private final String btnSolved;
    private final String btnDuplicate;
    private final String btnOpen;
    
    
    private TltCnf() {
        Properties properties = CnfUtil.load(CNF_FILE);
        pluginTlt = CnfUtil.readProperty(properties, "plugin.tlt");
        layerInfo = CnfUtil.readProperty(properties, "layer.info");
        prevMonth = CnfUtil.readProperty(properties, "cal.month.prev");
        nextMonth = CnfUtil.readProperty(properties, "cal.month.next");
        btnFilter = CnfUtil.readProperty(properties, "btn.filter");
        btnPhoto = CnfUtil.readProperty(properties, "btn.photo");
        btnTrip = CnfUtil.readProperty(properties, "btn.trip");
        btnComment = CnfUtil.readProperty(properties, "btn.comment");
        btnMoreAction = CnfUtil.readProperty(properties, "btn.more");
        btnInvalid = CnfUtil.readProperty(properties, "btn.invalid");
        btnSolved = CnfUtil.readProperty(properties, "btn.solved");
        btnDuplicate = CnfUtil.readProperty(properties, "btn.duplicate");
        btnOpen = CnfUtil.readProperty(properties, "btn.open");
    }
    
    
    public static TltCnf getInstance() {
        return INSTANCE;
    }
    
    public String getPluginTlt() {
        return pluginTlt;
    }
    
    public String getLayerInfo() {
        return layerInfo;
    }
    
    public String getPrevMonth() {
        return prevMonth;
    }
    
    public String getNextMonth() {
        return nextMonth;
    }
    
    public String getBtnFilter() {
        return btnFilter;
    }
    
    public String getBtnPhoto() {
        return btnPhoto;
    }
    
    public String getBtnTrip() {
        return btnTrip;
    }
    
    public String getBtnComment() {
        return btnComment;
    }
    
    public String getBtnMoreAction() {
        return btnMoreAction;
    }
    
    public String getBtnInvalid() {
        return btnInvalid;
    }
    
    public String getBtnSolved() {
        return btnSolved;
    }
    
    public String getBtnDuplicate() {
        return btnDuplicate;
    }
    
    public String getBtnOpen() {
        return btnOpen;
    }
}