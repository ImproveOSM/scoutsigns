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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.jdesktop.swingx.JXDatePicker;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateFormatter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.ConfidenceVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DateVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DuplicateIdVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ServiceCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


/**
 * Displays the possible road sign filters.
 * 
 * @author Beata
 * @version $Revision$
 */
class RoadSignFilterPanel extends JPanel {
    
    private static final long serialVersionUID = 31048161544787922L;
    
    private JXDatePicker pickerTo;
    private JXDatePicker pickerFrom;
    private StatusFilterPanel pnlStatus;
    private JList<String> listTypes;
    private JTextField txtDupl;
    private JTextField txtConf;
    private JTextField txtUsername;
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
        addConfidenceFilter(filter.getConfidence());
        addUsernameFilter(filter.getUsername());
        addDeviceFilter(filter.getDevice());
        addAppFilter(filter.getApp());
    }
    
    
    private void addTimeIntervalFilter(TimestampFilter tstampFilter) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblTimeInt(),
                FontUtil.BOLD_12, null), Constraints.LBL_INT);
        
        Date currentDate = Calendar.getInstance().getTime();
        Date lowerDate = tstampFilter != null && tstampFilter.getFrom() != null 
                ? new Date(tstampFilter.getFrom()) : null;
        Date upperDate = tstampFilter != null && tstampFilter.getTo() != null ?
                new Date(tstampFilter.getTo()) : currentDate;
                
        ImageIcon icon = IconCnf.getInstance().getCalendarIcon();
        
        pickerFrom = Builder.buildDatePicker(icon, new DateFormatter(),
                new FromChangeListener(), lowerDate, upperDate);
        DateVerifier fromVerifier = new DateVerifier(pickerFrom.getEditor(),
                GuiCnf.getInstance().getTxtDateInvalid());
        pickerFrom.getEditor().setInputVerifier(fromVerifier);
        add(pickerFrom, Constraints.CBB_START);
        
        pickerTo = Builder.buildDatePicker(icon, new DateFormatter(),
                new ToChangeListener(), lowerDate, upperDate);
        DateVerifier toVerifier = new DateVerifier(pickerTo.getEditor(),
                GuiCnf.getInstance().getTxtDateInvalid());
        pickerTo.getEditor().setInputVerifier(toVerifier);
        add(pickerTo, Constraints.CBB_END);
    }
    
    private void addStatusFilter(Status status) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblStatus(),
                FontUtil.BOLD_12, null), Constraints.LBL_STATUS);
        pnlStatus = new StatusFilterPanel(status);
        add(pnlStatus, Constraints.PNL_STATUS);
    }
    
    private void addTypeFilter(String type) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblType(),
                FontUtil.BOLD_12, null), Constraints.LBL_TYPE);
        listTypes = Builder.buildList(ServiceCnf.getInstance().getTypes(),
                ListSelectionModel.SINGLE_SELECTION, JList.HORIZONTAL_WRAP, type);
        JScrollPane cmpTypes = Builder.buildScrollPane(listTypes, Color.white, 
                false);
        cmpTypes.setPreferredSize(listTypes.getPreferredSize());
        add(cmpTypes, Constraints.LIST_TYPE);
    }
    
    private void addDuplicateFilter(Long duplicate) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblDupl(),
                FontUtil.BOLD_12, null), Constraints.LBL_DUPL);
        String txt = duplicate != null ? duplicate.toString() : "";
        txtDupl = Builder.buildTextField(txt, null, Color.white);
        add(txtDupl, Constraints.TXT_DUPL);
        txtDupl.setInputVerifier(new DuplicateIdVerifier(txtDupl, GuiCnf
                .getInstance().getTxtDuplIdInvalid()));
    }
    
    private void addConfidenceFilter(Short confidence) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblConf(),
                FontUtil.BOLD_12, null), Constraints.LBL_CONF);
        String txt = confidence != null ? confidence.toString() : "";
        txtConf = Builder.buildTextField(txt, null, Color.white);
        txtConf.setInputVerifier(new ConfidenceVerifier(txtConf, GuiCnf
                .getInstance().getTxtConfInvalid()));
        add(txtConf, Constraints.TXT_CONF);
    }
    
    private void addUsernameFilter(String username) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblUsername(),
                FontUtil.BOLD_12, null), Constraints.LBL_USERNAME);
        txtUsername = Builder.buildTextField(username, null, Color.white);
        add(txtUsername, Constraints.TXT_USERNAME);
    }
    
    private void addDeviceFilter(Device device) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblDevice(),
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
        add(Builder.buildLabel(GuiCnf.getInstance().getLblApp(),
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
        pickerFrom.setDate(null);
        pickerTo.setDate(null);
        pnlStatus.clearSelection();
        listTypes.clearSelection();
        txtDupl.setText("");
        txtConf.setText("" + SearchFilter.DEF_CONFIDENCE);
        txtUsername.setText("");
        txtAppName.setText("");
        txtAppVers.setText("");
        txtOsName.setText("");
        txtOsVers.setText("");
        repaint();
    }
    
    /**
     * Returns the selected filters. If the user's input is invalid the method
     * return null.
     * 
     * @return a {@code SearchFilter} object.
     */
    SearchFilter getSelectedFilter() {
        // verify text inputs
        SearchFilter filter = null;
        if (verifyInput()) {
            Long from = pickerFrom.getDate() != null ? 
                    pickerFrom.getDate().getTime() : null;
            
            Long to = pickerTo.getDate() != null ? pickerTo.getDate().getTime() 
                    : null;
            Status status = pnlStatus.getSelection();
            String type = listTypes.getSelectedValue();
            String duplicateStr = txtDupl.getText().trim();
            Long duplicate = !duplicateStr.isEmpty() ? 
                    Long.parseLong(duplicateStr) : null;
                    
            String confidenceStr = txtConf.getText().trim();
            Short confidence = !confidenceStr.isEmpty() ?
                    Short.parseShort(confidenceStr) : null;
                    
            String appName = txtAppName.getText().trim();
            String appVersion = txtAppVers.getText().trim();
            
            String osName = txtOsName.getText().trim();
            String osVersion = txtOsVers.getText().trim();
            
            String username = txtUsername.getText().trim();
            
            filter = new SearchFilter(new TimestampFilter(from, to), type,
                    status, duplicate, confidence, new Application(appName,
                            appVersion), new Device(osName, osVersion), username);
        }
        return filter;
    }
    
    private boolean verifyInput() {
        return txtDupl.getInputVerifier().verify(txtDupl) && 
                txtConf.getInputVerifier().verify(txtConf) && 
                pickerFrom.getEditor().getInputVerifier().verify(
                        pickerFrom.getEditor()) && 
                pickerTo.getEditor().getInputVerifier().verify(
                        pickerTo.getEditor());
    }
    
    
    /*
     * Listens to from date change value events.
     */
    private final class FromChangeListener implements PropertyChangeListener {
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("date")) {
                pickerTo.getMonthView().setLowerBound(pickerFrom.getDate());
            }
        }
        
    }
    
    
    /*
     * Listens to to date change value events.
     */
    private final class ToChangeListener implements PropertyChangeListener {
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("date")) {
                pickerFrom.getMonthView().setUpperBound(pickerTo.getDate());
            }
        }
        
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
                new GridBagConstraints(0, 2, 1, 1, 1, 1,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(7, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints PNL_STATUS =
                new GridBagConstraints(1, 2, 3, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 3, 3),
                        0, 0);
        private static final GridBagConstraints LBL_TYPE =
                new GridBagConstraints(0, 3, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LIST_TYPE =
                new GridBagConstraints(1, 3, 2, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 110);
        private static final GridBagConstraints LBL_DUPL =
                new GridBagConstraints(0, 4, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_DUPL =
                new GridBagConstraints(1, 4, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LBL_CONF =
                new GridBagConstraints(0, 5, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_CONF =
                new GridBagConstraints(1, 5, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LBL_USERNAME =
                new GridBagConstraints(0, 6, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_USERNAME =
                new GridBagConstraints(1, 6, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LBL_DEV =
                new GridBagConstraints(0, 7, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_OS_NAME =
                new GridBagConstraints(1, 7, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_OS_VERS =
                new GridBagConstraints(2, 7, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints LBL_APP =
                new GridBagConstraints(0, 8, 1, 1, 1, 0,
                        GridBagConstraints.PAGE_START,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_APP_NAME =
                new GridBagConstraints(1, 8, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
        private static final GridBagConstraints TXT_APP_VERS =
                new GridBagConstraints(2, 8, 1, 1, 1, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 5, 3, 5),
                        0, 0);
    }
}