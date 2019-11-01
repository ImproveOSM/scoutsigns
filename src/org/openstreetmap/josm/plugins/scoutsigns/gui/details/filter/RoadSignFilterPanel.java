/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.details.filter;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.ConfidenceVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DuplicateIdVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import com.telenav.josm.common.formatter.DateFormatter;
import com.telenav.josm.common.gui.builder.ContainerBuilder;
import com.telenav.josm.common.gui.builder.DatePickerBuilder;
import com.telenav.josm.common.gui.builder.LabelBuilder;
import com.telenav.josm.common.gui.builder.ListBuilder;
import com.telenav.josm.common.gui.builder.TextComponentBuilder;
import com.telenav.josm.common.gui.verifier.AbstractDateVerifier;


/**
 * Displays the possible road sign filters. The filters differ based on the selected source.
 *
 * @author Beata
 * @version $Revision$
 */
class RoadSignFilterPanel extends JPanel {

    private static final long serialVersionUID = 31048161544787922L;
    private static final Dimension TYPE_LIST_SIZE = new Dimension(300, 200);
    private static final Dimension PICKER_DIM = new Dimension(120, 20);

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

    private void addTimeIntervalFilter(final TimestampFilter tstampFilter) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblTimeInt(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_INT);

        final Date fromDate = tstampFilter.getFrom() != null ? new Date(tstampFilter.getFrom()) : null;
        final Date toDate = tstampFilter.getTo() != null ? new Date(tstampFilter.getTo()) : null;

        pickerFrom = DatePickerBuilder.build(fromDate, null, toDate, new DateFromChangeListener(), PICKER_DIM);
        pickerFrom.getEditor().addKeyListener(new DateVerifier(pickerFrom));
        add(pickerFrom, Constraints.CBB_START);

        pickerTo = DatePickerBuilder.build(toDate, fromDate, null, new DateToChangeListener(), PICKER_DIM);
        pickerTo.getEditor().addKeyListener(new DateVerifier(pickerTo));
        add(pickerTo, Constraints.CBB_END);
    }

    private void addTypeFilter(final List<String> selectedTypes) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblType(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_TYPE);
        listTypes = ListBuilder.build(Config.getInstance().getSignTypes(), selectedTypes, new TypeListCellRenderer(),
                Font.PLAIN);
        cmpTypes = ContainerBuilder.buildScrollPane(listTypes, Color.white,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cmpTypes.getViewport().setViewSize(TYPE_LIST_SIZE);
        add(cmpTypes, Constraints.LIST_TYPE);
    }

    private void addConfidenceFilter(final Double confidence) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblConf(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_CONF);
        final String txt = confidence != null ? Integer.toString(confidence.intValue()) : "";
        txtConf = TextComponentBuilder.buildTextField(txt, Font.PLAIN, Color.white);
        txtConf.setInputVerifier(new ConfidenceVerifier(txtConf, GuiConfig.getInstance().getTxtConfInvalid()));
        add(txtConf, Constraints.TXT_CONF);
    }

    private void addStatusFilter(final Status status) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblStatus(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_STATUS);
        pnlStatus = new StatusFilterPanel(status);
        add(pnlStatus, Constraints.PNL_STATUS);
    }

    private void addDuplicateFilter(final Long duplicate) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblDupl(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_DUPL);
        final String txt = duplicate != null ? duplicate.toString() : "";
        txtDupl = TextComponentBuilder.buildTextField(txt, Font.PLAIN, Color.white);
        txtDupl.setInputVerifier(new DuplicateIdVerifier(txtDupl, GuiConfig.getInstance().getTxtDuplIdInvalid()));
        add(txtDupl, Constraints.TXT_DUPL);
        txtDupl.setInputVerifier(new DuplicateIdVerifier(txtDupl, GuiConfig.getInstance().getTxtDuplIdInvalid()));
    }

    private void addUsernameFilter(final String username) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblUsername(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_USERNAME);
        txtUsername = TextComponentBuilder.buildTextField(username, Font.PLAIN, Color.white);
        add(txtUsername, Constraints.TXT_USERNAME);
    }

    private void addAppFilter(final Application application) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblApp(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_APP);
        String name = "";
        String vers = "";
        if (application != null) {
            name = application.getName();
            vers = application.getVersion();
        }
        txtAppName = TextComponentBuilder.buildTextField(name, Font.PLAIN, Color.white);
        add(txtAppName, Constraints.TXT_APP_NAME);
        txtAppVers = TextComponentBuilder.buildTextField(vers, Font.PLAIN, Color.white);
        add(txtAppVers, Constraints.TXT_APP_VERS);
    }

    private void addDeviceFilter(final Device device) {
        add(LabelBuilder.build(GuiConfig.getInstance().getLblDevice(), Font.BOLD, ComponentOrientation.LEFT_TO_RIGHT,
                SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_DEV);
        String name = "";
        String vers = "";
        if (device != null) {
            name = device.getOsName();
            vers = device.getOsVersion();
        }
        txtOsName = TextComponentBuilder.buildTextField(name, Font.PLAIN, Color.white);
        add(txtOsName, Constraints.TXT_OS_NAME);
        txtOsVers = TextComponentBuilder.buildTextField(vers, Font.PLAIN, Color.white);
        add(txtOsVers, Constraints.TXT_OS_VERS);
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
        txtConf.setText(Integer.toString(SearchFilter.DEF_CONFIDENCE.intValue()));
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

    private boolean verifyInput() {
        return txtDupl.getInputVerifier().verify(txtDupl) && txtConf.getInputVerifier().verify(txtConf)
                && validateDate(pickerFrom.getEditor().getText().trim())
                && validateDate(pickerTo.getEditor().getText().trim());
    }

    private boolean validateDate(final String valueStr) {
        boolean isValid = true;
        if (!valueStr.isEmpty()) {
            final Date newDate = DateFormatter.parseDay(valueStr);
            if (newDate == null) {
                isValid = false;
            }
        }
        return isValid;
    }

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

    private final class DateVerifier extends AbstractDateVerifier {

        private DateVerifier(final JXDatePicker component) {
            super(component);
        }

        @Override
        public boolean validate() {
            final String valueStr = getTextFieldValue();
            return validateDate(valueStr);
        }
    }
}