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
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateFormatter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.FontUtil;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.ConfidenceVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DateVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DuplicateIdVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;


/**
 * Displays the possible road sign filters. The filters differ based on the selected source.
 *
 * @author Beata
 * @version $Revision$
 */
class RoadSignFilterPanel extends JPanel {

    /*
     * Listens to from date change value events.
     */
    private class DateFromChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("date")) {
                pickerTo.getMonthView().setLowerBound(pickerFrom.getDate());
            }
        }
    }


    /*
     * Listens to to date change value events.
     */
    private class DateToChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("date")) {
                pickerFrom.getMonthView().setUpperBound(pickerTo.getDate());
            }
        }
    }

    private static final long serialVersionUID = 31048161544787922L;
    private static final Dimension TYPE_LIST_SIZE = new Dimension(300, 200);

    private JXDatePicker pickerTo;
    private JXDatePicker pickerFrom;
    private StatusFilterPanel pnlStatus;
    private JList<String> listTypes;
    private JScrollPane cmpTypes;
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
        final SearchFilter filter = PrefManager.getInstance().loadSearchFilter();
        addTimeIntervalFilter(filter.getTimestampFilter());
        addTypeFilter(filter.getTypes());
        addConfidenceFilter(filter.getConfidence());
        addStatusFilter(filter.getStatus());
        addDuplicateFilter(filter.getDuplicateOf());
        addUsernameFilter(filter.getUsername());
        addDeviceFilter(filter.getDevice());
        addAppFilter(filter.getApp());
        enableComponents();
    }


    private void addAppFilter(final Application application) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblApp(), FontUtil.BOLD_12, null), Constraints.LBL_APP);
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

    private void addConfidenceFilter(final Double confidence) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblConf(), FontUtil.BOLD_12, null), Constraints.LBL_CONF);
        final String txt = confidence != null ? "" + confidence.intValue() : "";
        txtConf = Builder.buildTextField(txt, null, Color.white);
        txtConf.setInputVerifier(new ConfidenceVerifier(txtConf, GuiConfig.getInstance().getTxtConfInvalid()));
        add(txtConf, Constraints.TXT_CONF);
    }

    private void addDeviceFilter(final Device device) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblDevice(), FontUtil.BOLD_12, null), Constraints.LBL_DEV);
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

    private void addDuplicateFilter(final Long duplicate) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblDupl(), FontUtil.BOLD_12, null), Constraints.LBL_DUPL);
        final String txt = duplicate != null ? duplicate.toString() : "";
        txtDupl = Builder.buildTextField(txt, null, Color.white);
        add(txtDupl, Constraints.TXT_DUPL);
        txtDupl.setInputVerifier(new DuplicateIdVerifier(txtDupl, GuiConfig.getInstance().getTxtDuplIdInvalid()));
    }

    private void addStatusFilter(final Status status) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblStatus(), FontUtil.BOLD_12, null), Constraints.LBL_STATUS);
        pnlStatus = new StatusFilterPanel(status);
        add(pnlStatus, Constraints.PNL_STATUS);
    }

    private void addTimeIntervalFilter(final TimestampFilter tstampFilter) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblTimeInt(), FontUtil.BOLD_12, null), Constraints.LBL_INT);
        Date lower = null;
        Date upper = null;
        Long from = null;
        Long to = null;
        if (tstampFilter != null) {
            from = tstampFilter.getFrom();
            to = tstampFilter.getTo();
            lower = tstampFilter.getFrom() != null ? new Date(tstampFilter.getFrom()) : null;
            upper = tstampFilter.getTo() != null ? new Date(tstampFilter.getTo()) : Calendar.getInstance().getTime();
        }

        pickerFrom = Builder.buildDatePicker(IconConfig.getInstance().getCalendarIcon(), new DateFormatter(),
                new DateFromChangeListener(), null, upper, lower);
        pickerFrom.getEditor().setText(DateUtil.formatDay(from));
        pickerFrom.getEditor().setInputVerifier(
                new DateVerifier(pickerFrom.getEditor(), GuiConfig.getInstance().getTxtDateInvalid()));
        add(pickerFrom, Constraints.CBB_START);

        pickerTo = Builder.buildDatePicker(IconConfig.getInstance().getCalendarIcon(), new DateFormatter(),
                new DateToChangeListener(), lower, upper, null);
        pickerTo.getEditor().setText(DateUtil.formatDay(to));
        pickerTo.getEditor()
        .setInputVerifier(new DateVerifier(pickerTo.getEditor(), GuiConfig.getInstance().getTxtDateInvalid()));
        add(pickerTo, Constraints.CBB_END);
    }

    private void addTypeFilter(final List<String> selectedTypes) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblType(), FontUtil.BOLD_12, null), Constraints.LBL_TYPE);
        listTypes = Builder.buildList(Config.getInstance().getSignTypes(), new TypeListCellRenderer(), selectedTypes);
        cmpTypes = Builder.buildScrollPane(listTypes, Color.white, false);
        cmpTypes.getViewport().setViewSize(TYPE_LIST_SIZE);
        add(cmpTypes, Constraints.LIST_TYPE);
    }

    private void addUsernameFilter(final String username) {
        add(Builder.buildLabel(GuiConfig.getInstance().getLblUsername(), FontUtil.BOLD_12, null),
                Constraints.LBL_USERNAME);
        txtUsername = Builder.buildTextField(username, null, Color.white);
        add(txtUsername, Constraints.TXT_USERNAME);
    }


    private void enableComponents() {
        pnlStatus.enableComponents(true);
        txtDupl.setEnabled(true);
        txtUsername.setEnabled(true);
        txtOsName.setEnabled(true);
        txtOsVers.setEnabled(true);
        txtAppName.setEnabled(true);
        txtAppVers.setEnabled(true);

    }

    private boolean verifyInput() {
        return txtDupl.getInputVerifier().verify(txtDupl) && txtConf.getInputVerifier().verify(txtConf)
                && pickerFrom.getEditor().getInputVerifier().verify(pickerFrom.getEditor())
                && pickerTo.getEditor().getInputVerifier().verify(pickerTo.getEditor());
    }

    /**
     * Resets the filters to the default one.
     */
    void resetFilters() {
        pickerFrom.getEditor().setText("");
        pickerFrom.setDate(null);
        pickerTo.getEditor().setText("");
        pickerTo.setDate(null);
        pnlStatus.clearSelection();
        txtDupl.setText("");
        txtConf.setText("" + SearchFilter.DEF_CONFIDENCE.intValue());
        txtUsername.setText("");
        txtAppName.setText("");
        txtAppVers.setText("");
        txtOsName.setText("");
        txtOsVers.setText("");
        listTypes.clearSelection();
        enableComponents();
    }

    /**
     * Returns the selected filters. If the user's input is invalid the method return null.
     *
     * @return a {@code SearchFilter} object.
     */
    SearchFilter selectedFilters() {
        // verify text inputs
        SearchFilter filter = null;
        if (verifyInput()) {
            final Long from = pickerFrom.getDate() != null ? pickerFrom.getDate().getTime() : null;
            final Long to = pickerTo.getDate() != null ? pickerTo.getDate().getTime() : null;
            final Status status = pnlStatus.getSelection();
            final String duplicateStr = txtDupl.getText().trim();
            final Long duplicate = !duplicateStr.isEmpty() ? Long.parseLong(duplicateStr) : null;
            final String confidenceStr = txtConf.getText().trim();
            final Double confidence = !confidenceStr.isEmpty() ? Double.parseDouble(confidenceStr) : null;
            final String appName = txtAppName.getText().trim();
            final String appVersion = txtAppVers.getText().trim();
            final String osName = txtOsName.getText().trim();
            final String osVersion = txtOsVers.getText().trim();
            final String username = txtUsername.getText().trim();
            filter = new SearchFilter(new TimestampFilter(from, to), listTypes.getSelectedValuesList(), status,
                    duplicate, confidence, new Application(appName, appVersion), new Device(osName, osVersion),
                    username);
        }
        return filter;
    }
}