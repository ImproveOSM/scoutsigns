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
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import org.jdesktop.swingx.JXDatePicker;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.ConfidenceVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DateVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.gui.verifier.DuplicateIdVerifier;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.Config;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.pref.PrefManager;
import com.telenav.josm.common.formatter.DateFormatter;
import com.telenav.josm.common.gui.GuiBuilder;
import com.telenav.josm.common.util.DateUtil;


/**
 * Displays the possible road sign filters. The filters differ based on the selected source.
 *
 * @author Beata
 * @version $Revision$
 */
class RoadSignFilterPanel extends JPanel {

    private static final Dimension PICKER_DIM = new Dimension(120, 20);
    private static final Dimension PICKER_BTN_DIM = new Dimension(20, 20);


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
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblApp(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_APP);
        String name = "";
        String vers = "";
        if (application != null) {
            name = application.getName();
            vers = application.getVersion();
        }
        txtAppName = GuiBuilder.buildTextField(name, getFont().deriveFont(Font.PLAIN), Color.white);
        add(txtAppName, Constraints.TXT_APP_NAME);
        txtAppVers = GuiBuilder.buildTextField(vers, getFont().deriveFont(Font.PLAIN), Color.white);
        add(txtAppVers, Constraints.TXT_APP_VERS);
    }

    private void addConfidenceFilter(final Double confidence) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblConf(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_CONF);
        final String txt = confidence != null ? "" + confidence.intValue() : "";
        txtConf = GuiBuilder.buildTextField(txt, getFont().deriveFont(Font.PLAIN), Color.white);
        txtConf.setInputVerifier(new ConfidenceVerifier(txtConf, GuiConfig.getInstance().getTxtConfInvalid()));
        add(txtConf, Constraints.TXT_CONF);
    }

    private void addDeviceFilter(final Device device) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblDevice(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_DEV);
        String name = "";
        String vers = "";
        if (device != null) {
            name = device.getOsName();
            vers = device.getOsVersion();
        }
        txtOsName = GuiBuilder.buildTextField(name, getFont().deriveFont(Font.PLAIN), Color.white);
        add(txtOsName, Constraints.TXT_OS_NAME);
        txtOsVers = GuiBuilder.buildTextField(vers, getFont().deriveFont(Font.PLAIN), Color.white);
        add(txtOsVers, Constraints.TXT_OS_VERS);
    }

    private void addDuplicateFilter(final Long duplicate) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblDupl(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_DUPL);
        final String txt = duplicate != null ? duplicate.toString() : "";
        txtDupl = GuiBuilder.buildTextField(txt, getFont().deriveFont(Font.PLAIN), Color.white);
        add(txtDupl, Constraints.TXT_DUPL);
        txtDupl.setInputVerifier(new DuplicateIdVerifier(txtDupl, GuiConfig.getInstance().getTxtDuplIdInvalid()));
    }

    private void addStatusFilter(final Status status) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblStatus(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_STATUS);
        pnlStatus = new StatusFilterPanel(status);
        add(pnlStatus, Constraints.PNL_STATUS);
    }

    private void addTimeIntervalFilter(final TimestampFilter tstampFilter) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblTimeInt(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_INT);
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

        pickerFrom = buildDatePicker(IconConfig.getInstance().getCalendarIcon(), new DateFormatter(),
                new DateFromChangeListener(), null, upper, lower);
        pickerFrom.getEditor().setText(DateUtil.formatDay(from));
        pickerFrom.getEditor().setInputVerifier(
                new DateVerifier(pickerFrom.getEditor(), GuiConfig.getInstance().getTxtDateInvalid()));
        add(pickerFrom, Constraints.CBB_START);

        pickerTo = buildDatePicker(IconConfig.getInstance().getCalendarIcon(), new DateFormatter(),
                new DateToChangeListener(), lower, upper, null);
        pickerTo.getEditor().setText(DateUtil.formatDay(to));
        pickerTo.getEditor()
        .setInputVerifier(new DateVerifier(pickerTo.getEditor(), GuiConfig.getInstance().getTxtDateInvalid()));
        add(pickerTo, Constraints.CBB_END);
    }

    private void addTypeFilter(final List<String> selectedTypes) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblType(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_TYPE);
        listTypes = buildList(Config.getInstance().getSignTypes(), new TypeListCellRenderer(), selectedTypes);

        cmpTypes = GuiBuilder.buildScrollPane(listTypes, Color.white, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cmpTypes.getViewport().setViewSize(TYPE_LIST_SIZE);
        add(cmpTypes, Constraints.LIST_TYPE);
    }

    private <T> JList<T> buildList(final List<T> data, final DefaultListCellRenderer renderer,
            final List<T> selection) {
        final DefaultListModel<T> model = new DefaultListModel<>();
        for (final T elem : data) {
            model.addElement(elem);
        }
        final JList<T> list = new JList<>(model);
        list.setFont(getFont().deriveFont(Font.PLAIN));
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setCellRenderer(renderer);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (selection != null && !selection.isEmpty()) {
                    for (final T sel : selection) {
                        final int idx = model.indexOf(sel);
                        list.addSelectionInterval(idx, idx);
                    }
                    list.ensureIndexIsVisible(list.getSelectedIndex());
                    list.scrollRectToVisible(
                            list.getCellBounds(list.getMinSelectionIndex(), list.getMaxSelectionIndex()));
                }
            }
        });
        return list;
    }

    private void addUsernameFilter(final String username) {
        add(GuiBuilder.buildLabel(GuiConfig.getInstance().getLblUsername(), getFont().deriveFont(Font.BOLD),
                ComponentOrientation.LEFT_TO_RIGHT, SwingConstants.LEFT, SwingConstants.TOP), Constraints.LBL_USERNAME);
        txtUsername = GuiBuilder.buildTextField(username, getFont().deriveFont(Font.PLAIN), Color.white);
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

    private JXDatePicker buildDatePicker(final Icon icon, final AbstractFormatter formatter,
            final PropertyChangeListener changeListener, final Date lowerDate, final Date upperDate,
            final Date selDate) {
        final JXDatePicker picker = new JXDatePicker();

        picker.setPreferredSize(PICKER_DIM);

        // customize month view
        picker.getMonthView().setTodayBackground(Color.darkGray);
        picker.getMonthView().setDayForeground(Calendar.SATURDAY, Color.red);
        picker.getMonthView().setShowingLeadingDays(true);
        picker.getMonthView().setShowingTrailingDays(true);
        picker.getMonthView().setLowerBound(lowerDate);
        picker.getMonthView().setUpperBound(upperDate);
        picker.getMonthView().setSelectionDate(selDate);

        // customize button
        ((JButton) picker.getComponent(1)).setIcon(icon);
        ((JButton) picker.getComponent(1)).setPreferredSize(PICKER_BTN_DIM);

        // customize editor
        picker.getEditor().setFormatterFactory(new DefaultFormatterFactory(formatter));

        // add listener
        picker.addPropertyChangeListener(changeListener);

        return picker;
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
}