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
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Source;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.gui.Builder;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateFormatter;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateUtil;
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


    /*
     * Listens to from date change value events.
     */
    private final class DateFromChangeListener implements PropertyChangeListener {

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
    private final class DateToChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("date")) {
                pickerFrom.getMonthView().setUpperBound(pickerTo.getDate());
            }
        }
    }

    /*
     * Listens to source filter changes.
     */
    private final class SourceSelectionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent event) {
            List<String> types;
            DefaultListCellRenderer renderer;
            final List<Source> sources = new ArrayList<>();
            if (cbbScout.isSelected() && !cbbMapillary.isSelected()) {
                pnlStatus.enableComponents(true);
                txtDupl.setEnabled(true);
                txtUsername.setEnabled(true);
                txtOsName.setEnabled(true);
                txtOsVers.setEnabled(true);
                txtAppName.setEnabled(true);
                txtAppVers.setEnabled(true);

                // reload scout types
                sources.add(Source.SCOUT);
                types = ServiceCnf.getInstance().getScoutTypes();
                renderer = new TypeListCellRenderer(Source.SCOUT);
            } else {
                // enable common filters, time interval, type, confidence
                pnlStatus.enableComponents(false);
                txtDupl.setEnabled(false);
                txtUsername.setEnabled(false);
                txtOsName.setEnabled(false);
                txtOsVers.setEnabled(false);
                txtAppName.setEnabled(false);
                txtAppVers.setEnabled(false);

                if (cbbMapillary.isSelected() && !cbbScout.isSelected()) {
                    // reload Mapillary types
                    sources.add(Source.MAPILLARY);
                    types = ServiceCnf.getInstance().getMapillaryTypes();
                    renderer = new TypeListCellRenderer(Source.MAPILLARY);
                } else {
                    types = ServiceCnf.getInstance().getCommonTypes();
                    renderer = new TypeListCellRenderer(null);
                }
            }
            listTypes = Builder.buildList(types, renderer, null);
            cmpTypes.getViewport().setView(listTypes);
            repaint();
        }
    }

    private static final long serialVersionUID = 31048161544787922L;

    private static final Dimension TYPE_LIST_SIZE = new Dimension(300, 200);

    private JCheckBox cbbScout;
    private JCheckBox cbbMapillary;
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
        addStatusFilter(filter.getStatus());
        addTypeFilter(filter.getSources(), filter.getTypes());
        addDuplicateFilter(filter.getDuplicateOf());
        addConfidenceFilter(filter.getConfidence());
        addUsernameFilter(filter.getUsername());
        addDeviceFilter(filter.getDevice());
        addAppFilter(filter.getApp());
        addSourceFilter(filter.getSources());
    }

    /**
     * Returns the selected filters. If the user's input is invalid the method return null.
     *
     * @return a {@code SearchFilter} object.
     */
    SearchFilter getSelectedFilter() {
        // verify text inputs
        SearchFilter filter = null;
        if (verifyInput()) {
            final Long from = pickerFrom.getDate() != null ? pickerFrom.getDate().getTime() : null;
            final Long to = pickerTo.getDate() != null ? pickerTo.getDate().getTime() : null;
            final Status status = pnlStatus.getSelection();
            final String duplicateStr = txtDupl.getText().trim();
            final Long duplicate = !duplicateStr.isEmpty() ? Long.parseLong(duplicateStr) : null;
            final String confidenceStr = txtConf.getText().trim();
            final Short confidence = !confidenceStr.isEmpty() ? Short.parseShort(confidenceStr) : null;
            final String appName = txtAppName.getText().trim();
            final String appVersion = txtAppVers.getText().trim();
            final String osName = txtOsName.getText().trim();
            final String osVersion = txtOsVers.getText().trim();
            final String username = txtUsername.getText().trim();
            final List<Source> sources = getSelectedSources();
            filter =
                    new SearchFilter(sources, new TimestampFilter(from, to), listTypes.getSelectedValuesList(), status,
                            duplicate, confidence, new Application(appName, appVersion), new Device(osName, osVersion),
                            username);
        }
        return filter;
    }

    /**
     * Resets the filters to the default one.
     */
    void resetFilters() {
        cbbScout.setSelected(true);
        cbbMapillary.setSelected(true);
        listTypes =
                Builder.buildList(ServiceCnf.getInstance().getScoutTypes(), new TypeListCellRenderer(Source.SCOUT),
                        null);
        cmpTypes.getViewport().setView(listTypes);
        pickerFrom.getEditor().setText("");
        pickerFrom.setDate(null);
        pickerTo.getEditor().setText("");
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

    private void addAppFilter(final Application application) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblApp(), FontUtil.BOLD_12, null), Constraints.LBL_APP);
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

    private void addConfidenceFilter(final Short confidence) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblConf(), FontUtil.BOLD_12, null), Constraints.LBL_CONF);
        final String txt = confidence != null ? confidence.toString() : "";
        txtConf = Builder.buildTextField(txt, null, Color.white);
        txtConf.setInputVerifier(new ConfidenceVerifier(txtConf, GuiCnf.getInstance().getTxtConfInvalid()));
        add(txtConf, Constraints.TXT_CONF);
    }

    private void addDeviceFilter(final Device device) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblDevice(), FontUtil.BOLD_12, null), Constraints.LBL_DEV);
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
        add(Builder.buildLabel(GuiCnf.getInstance().getLblDupl(), FontUtil.BOLD_12, null), Constraints.LBL_DUPL);
        final String txt = duplicate != null ? duplicate.toString() : "";
        txtDupl = Builder.buildTextField(txt, null, Color.white);
        add(txtDupl, Constraints.TXT_DUPL);
        txtDupl.setInputVerifier(new DuplicateIdVerifier(txtDupl, GuiCnf.getInstance().getTxtDuplIdInvalid()));
    }

    private void addSourceFilter(final List<Source> sources) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblSources(), FontUtil.BOLD_12, null), Constraints.LBL_SOURCES);

        boolean scoutSel = false;
        boolean mapillarySel = false;
        if (sources != null) {
            scoutSel = sources.contains(Source.SCOUT);
            mapillarySel = sources.contains(Source.MAPILLARY);
        }
        cbbScout = Builder.buildCheckBox(new SourceSelectionListener(), Source.SCOUT.name().toLowerCase(), scoutSel);
        cbbMapillary =
                Builder.buildCheckBox(new SourceSelectionListener(), Source.MAPILLARY.name().toLowerCase(),
                        mapillarySel);
        final JPanel pnl = new JPanel(new GridBagLayout());
        pnl.add(cbbScout, Constraints.CBB_SCOUT);
        pnl.add(cbbMapillary, Constraints.CBB_MAPILLARY);
        add(pnl, Constraints.PNL_SOURCES);
    }

    private void addStatusFilter(final Status status) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblStatus(), FontUtil.BOLD_12, null), Constraints.LBL_STATUS);
        pnlStatus = new StatusFilterPanel(status);
        add(pnlStatus, Constraints.PNL_STATUS);
    }

    private void addTimeIntervalFilter(final TimestampFilter tstampFilter) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblTimeInt(), FontUtil.BOLD_12, null), Constraints.LBL_INT);
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

        pickerFrom =
                Builder.buildDatePicker(IconCnf.getInstance().getCalendarIcon(), new DateFormatter(),
                        new DateFromChangeListener(), null, upper, lower);
        pickerFrom.getEditor().setText(DateUtil.formatDay(from));
        final DateVerifier fromVerifier =
                new DateVerifier(pickerFrom.getEditor(), GuiCnf.getInstance().getTxtDateInvalid());
        pickerFrom.getEditor().setInputVerifier(fromVerifier);
        add(pickerFrom, Constraints.CBB_START);

        pickerTo =
                Builder.buildDatePicker(IconCnf.getInstance().getCalendarIcon(), new DateFormatter(),
                        new DateToChangeListener(), lower, upper, null);
        pickerTo.getEditor().setText(DateUtil.formatDay(to));
        final DateVerifier toVerifier =
                new DateVerifier(pickerTo.getEditor(), GuiCnf.getInstance().getTxtDateInvalid());
        pickerTo.getEditor().setInputVerifier(toVerifier);
        add(pickerTo, Constraints.CBB_END);
    }


    private void addTypeFilter(final List<Source> sources, final List<String> selectedTypes) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblType(), FontUtil.BOLD_12, null), Constraints.LBL_TYPE);

        List<String> types;
        DefaultListCellRenderer renderer;
        if (sources == null || sources.size() == 2) {
            types = ServiceCnf.getInstance().getCommonTypes();
            renderer = new TypeListCellRenderer(null);
        } else if (sources.contains(Source.SCOUT)) {
            types = ServiceCnf.getInstance().getScoutTypes();
            renderer = new TypeListCellRenderer(Source.SCOUT);
        } else {
            types = ServiceCnf.getInstance().getMapillaryTypes();
            renderer = new TypeListCellRenderer(Source.MAPILLARY);
        }

        listTypes = Builder.buildList(types, renderer, selectedTypes);
        cmpTypes = Builder.buildScrollPane(listTypes, Color.white, false);
        cmpTypes.getViewport().setViewSize(TYPE_LIST_SIZE);
        add(cmpTypes, Constraints.LIST_TYPE);
    }


    private void addUsernameFilter(final String username) {
        add(Builder.buildLabel(GuiCnf.getInstance().getLblUsername(), FontUtil.BOLD_12, null), Constraints.LBL_USERNAME);
        txtUsername = Builder.buildTextField(username, null, Color.white);
        add(txtUsername, Constraints.TXT_USERNAME);
    }

    private List<Source> getSelectedSources() {
        final List<Source> sources = new ArrayList<Source>();
        if (cbbScout.isSelected()) {
            sources.add(Source.SCOUT);
        }
        if (cbbMapillary.isSelected()) {
            sources.add(Source.MAPILLARY);
        }
        return sources;
    }

    private boolean verifyInput() {
        return txtDupl.getInputVerifier().verify(txtDupl) && txtConf.getInputVerifier().verify(txtConf)
                && pickerFrom.getEditor().getInputVerifier().verify(pickerFrom.getEditor())
                && pickerTo.getEditor().getInputVerifier().verify(pickerTo.getEditor());
    }
}