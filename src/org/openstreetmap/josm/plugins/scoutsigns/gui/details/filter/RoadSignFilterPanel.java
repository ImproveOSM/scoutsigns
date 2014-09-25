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
 * Created on Sep 24, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.calendar.CalendarComboBox;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


/**
 * Displays the possible road sign filters.
 * 
 * @author Beata
 * @version $Revision$
 */
class RoadSignFilterPanel extends JPanel {
    
    private static final long serialVersionUID = 31048161544787922L;
    
    private CalendarComboBox cbboxStart;
    private CalendarComboBox cbboxEnd;
    private StatusFilterPanel pnlStatus;
    private TypeFilterPanel pnlType;
    private JTextField txtDuplicate;
    private JTextField txtOsName;
    private JTextField txtOsVers;
    private JTextField txtAppName;
    private JTextField txtAppVers;
    
    
    /**
     * Builds a new road sign filter panel.
     */
    RoadSignFilterPanel() {
        super(new GridBagLayout());
        
        // create and add panel components
        SearchFilter filter = PrefManager.getInstance().loadSearchFilter();
        addTimeIntervalFilter(filter.getTimestampFilter());
        addStatusFilter(filter.getStatus());
        addTypeFilter(filter.getType());
        addDuplicateFilter(filter.getDuplicateOf());
        addDeviceFilter(filter.getDevice());
        addAppFilter(filter.getApp());
    }
    
    
    private void addTimeIntervalFilter(TimestampFilter tstampFilter) {
        add(Builder.buildLabel(GuiCnf.getInstance().getTimeIntLbl(),
                FontUtil.BOLD_12, null), Constraints.LBL_INT);
        Long from = tstampFilter != null ? tstampFilter.getFrom() : null;
        cbboxStart = new CalendarComboBox(from);
        add(cbboxStart, Constraints.CBB_START);
        cbboxEnd = new CalendarComboBox(null);
        add(cbboxEnd, Constraints.CBB_END);
    }
    
    private void addStatusFilter(Status status) {
        add(Builder.buildLabel(GuiCnf.getInstance().getStatusLbl(),
                FontUtil.BOLD_12, null), Constraints.LBL_STATUS);
        pnlStatus = new StatusFilterPanel(status);
        add(pnlStatus, Constraints.PNL_STATUS);
    }
    
    private void addTypeFilter(String type) {
        add(Builder.buildLabel(GuiCnf.getInstance().getTypeLbl(),
                FontUtil.BOLD_12, null), Constraints.LBL_TYPE);
        pnlType = new TypeFilterPanel(type);
        add(Builder.buildScrollPane(pnlType, Color.white),
                Constraints.LIST_TYPE);
    }
    
    private void addDuplicateFilter(Long duplicate) {
        add(Builder.buildLabel(GuiCnf.getInstance().getDuplLbl(),
                FontUtil.BOLD_12, null), Constraints.LBL_DUPL);
        String txt = duplicate != null ? duplicate.toString() : "";
        txtDuplicate = Builder.buildTextField(txt, null, Color.white);
        add(txtDuplicate, Constraints.TXT_DUPL);
    }
    
    private void addDeviceFilter(Device device) {
        add(Builder.buildLabel(GuiCnf.getInstance().getDeviceLbl(),
                FontUtil.BOLD_12, null), Constraints.LBL_DEV);
        String name = "";
        String vers = "";
        if (device != null) {
            name = device.getOsName();
            vers = device.getOsVersion();
        }
        txtOsName = Builder.buildTextField(name, null, Color.white);
        add(txtOsName, Constraints.TXT_OS_NAME);
        txtOsVers = Builder.buildTextField(vers, null, Color.white);
        add(txtOsVers, Constraints.TXT_OS_VERS);
    }
    
    private void addAppFilter(Application application) {
        add(Builder.buildLabel(GuiCnf.getInstance().getAppLbl(),
                FontUtil.BOLD_12, null), Constraints.LBL_APP);
        String name = "";
        String vers = "";
        if (application != null) {
            name = application.getName();
            vers = application.getVersion();
        }
        txtAppName = Builder.buildTextField(name, null, Color.white);
        add(txtAppName, Constraints.TXT_APP_NAME);
        txtAppVers = Builder.buildTextField(vers, null, Color.white);
        add(txtAppVers, Constraints.TXT_APP_VERS);
    }
    
    /**
     * Resets the filters to the default one.
     */
    void resetFilters() {
        cbboxStart.setSelectedIndex(-1);
        cbboxEnd.setSelectedIndex(-1);
        pnlStatus.clearSelection();
        pnlType.clearSelection();
        txtDuplicate.setText("");
        txtAppName.setText("");
        txtAppVers.setText("");
        txtOsName.setText("");
        txtOsVers.setText("");
        repaint();
    }
    
    /**
     * Returns the selected filters.
     * 
     * @return a {@code SearchFilter} object.
     */
    SearchFilter getSelectedFilter() {
        String fromStr = (String) cbboxStart.getSelectedItem();
        Date fromDate = DateUtil.parseDay(fromStr);
        Long from = fromDate != null ? fromDate.getTime() :null;
        
        String toStr = (String) cbboxEnd.getSelectedItem();
        Date toDate = DateUtil.parseDay(toStr);
        Long to = toDate != null ? toDate.getTime() :null;
        
        Status status = pnlStatus.getSelection();
        String type = pnlType.getSelection();
        Long duplicate = txtDuplicate.getText() != null && 
                !txtDuplicate.getText().isEmpty() ? 
                        Long.parseLong(txtDuplicate.getText()) : null;
        String appName = txtAppName.getText();
        String appVersion = txtAppVers.getText();
        String osName = txtOsName.getText();
        String osVersion = txtOsVers.getText();
        
        return new SearchFilter(new TimestampFilter(from, to), type, status,
                duplicate, new Application(appName, appVersion), new Device(
                        osName, osVersion));
    }
    
    private static final class Constraints {
        
        private Constraints() {}
        
        private static final GridBagConstraints LBL_INT =
                new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(7, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints CBB_START =
                new GridBagConstraints(1, 0, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(7, 5, 2, 5),
                        0, 0);
        private static final GridBagConstraints CBB_END =
                new GridBagConstraints(2, 0, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(7, 5, 2, 5),
                        0, 0);
        private static final GridBagConstraints LBL_STATUS =
                new GridBagConstraints(0, 1, 1, 1, 1, 1,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(7, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints PNL_STATUS =
                new GridBagConstraints(1, 1, 2, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 3, 5),
                        0, 0);
        private static final GridBagConstraints LBL_TYPE =
                new GridBagConstraints(0, 2, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LIST_TYPE =
                new GridBagConstraints(1, 2, 2, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 70);
        private static final GridBagConstraints LBL_DUPL =
                new GridBagConstraints(0, 3, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_DUPL =
                new GridBagConstraints(1, 3, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LBL_DEV =
                new GridBagConstraints(0, 4, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_OS_NAME =
                new GridBagConstraints(1, 4, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_OS_VERS =
                new GridBagConstraints(2, 4, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LBL_APP =
                new GridBagConstraints(0, 5, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_APP_NAME =
                new GridBagConstraints(1, 5, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_APP_VERS =
                new GridBagConstraints(2, 5, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
    }
}