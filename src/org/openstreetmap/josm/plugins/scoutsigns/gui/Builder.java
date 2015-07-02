/*
 * Copyright (c) 2012, skobbler GmbH
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
 * Created on Jun 21, 2012 by Bea
 * Modified on $Date$
 *          by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import org.jdesktop.swingx.JXDatePicker;


/**
 * Helper object, used for creating specific GUI elements.
 *
 * @author Bea
 * @version $Revision$
 */
public final class Builder {

    private static final int HEADER_WIDTH = 12;

    private static final Dimension PICKER_DIM = new Dimension(120, 20);
    private static final Dimension PICKER_BTN_DIM = new Dimension(20, 20);

    /**
     * Builds a box layout {@code JPanel} with the given components.
     *
     * @param cmpLeft the left component
     * @param cmpCenter the center component
     * @param cmpRight the right component
     * @return a {@code JPanel} object
     */
    public static JPanel buildBoxLayoutPanel(final JComponent cmpLeft, final JComponent cmpCenter,
            final JComponent cmpRight) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.lightGray);
        panel.setOpaque(true);
        panel.add(Box.createHorizontalStrut(HEADER_WIDTH));
        panel.add(cmpLeft);
        panel.add(Box.createHorizontalStrut(HEADER_WIDTH));
        panel.add(Box.createHorizontalGlue());
        panel.add(cmpCenter);
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalStrut(HEADER_WIDTH));
        panel.add(cmpRight);
        panel.add(Box.createHorizontalStrut(HEADER_WIDTH));
        return panel;
    }


    /**
     * Builds a {@code JButton} object with the given properties.
     *
     * @param action the {@code AbstractAction} to be executed when the button is clicked
     * @param icon the {@code Icon} to be displayed on the button
     * @param tooltip the tooltip to display on mouse hover
     * @return a {@code JButton}
     */
    public static JButton buildButton(final AbstractAction action, final Icon icon, final String tooltip) {
        JButton btn;
        if (action == null) {
            btn = new JButton();
        } else {
            btn = new JButton(action);
        }
        btn.setIcon(icon);
        btn.setToolTipText(tooltip);
        btn.setFocusable(false);
        return btn;
    }

    /**
     * Builds a {@code JButton} with the given arguments.
     *
     * @param action the action to be executed when the button is clicked
     * @param text the text to be displayed
     * @return a {@code JButton} object
     */
    public static JButton buildButton(final AbstractAction action, final String text) {
        final JButton btn = new JButton(action);
        btn.setFont(FontUtil.BOLD_12);
        btn.setText(text);
        btn.setFocusable(false);
        return btn;
    }

    /**
     * Builds a {@code JCheckBox} with the given arguments.
     *
     * @param listener the listener to be executed when the check box is selected
     * @param text the text to be displayed
     * @param isSelected specifies if the check-box is selected or not
     * @return a {@code JCheckBox} object
     */
    public static JCheckBox buildCheckBox(final ActionListener listener, final String text, final boolean isSelected) {
        final JCheckBox cbbox = new JCheckBox(text);
        if (listener != null) {
            cbbox.addActionListener(listener);
        }
        cbbox.setFont(FontUtil.PLAIN_12);
        cbbox.setFocusPainted(false);
        cbbox.setSelected(isSelected);
        return cbbox;
    }

    /**
     * Builds a new {@code JXDatePicker} with the given arguments.
     *
     * @param icon the {@code Icon} to be displayed on the action button
     * @param formatter custom {@code AbstractFormatter} used for formatting the user's input
     * @param changeListener {@code PropertyChangeListener} defining the action to be executed when the controller's
     * value is changed
     * @param lowerDate the lower {@code Date} limit to be set
     * @param upperDate the upper {@code Date} limit to be set
     * @param selDate the selected {@code Date}
     * @return a {@code JXDatePicker} object
     */
    public static JXDatePicker
    buildDatePicker(final Icon icon, final AbstractFormatter formatter,
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
     * Builds a {@code JLabel} with the given properties.
     *
     * @param icon the {@code Icon} to be displayed on the label
     * @param bounds the dimension and location of the label
     * @return a {@code JLabel}
     */
    public static JLabel buildLabel(final Icon icon, final Rectangle bounds) {
        final JLabel lbl = new JLabel(icon);
        if (bounds != null) {
            lbl.setBounds(bounds);
        }
        return lbl;
    }

    /**
     * Builds a {@code JLabel} with the given properties.
     *
     * @param text the text which will be shown on the label
     * @param font the font of the label's text
     * @param textColor the text color
     * @param visible specifies if the label is visible or not
     * @return a new {@code JLabel} object
     */
    public static JLabel buildLabel(final String text, final Font font, final Color textColor, final boolean visible) {
        final JLabel lbl = buildLabel(text, font, null);
        lbl.setForeground(textColor);
        lbl.setVisible(visible);
        return lbl;
    }

    /**
     * Builds a {@code JLabel} with the given properties.
     *
     * @param text the text which will be shown on the label
     * @param font the font of the label's text
     * @param bounds the dimension and location of the label
     * @return a new {@code JLabel} object
     */
    public static JLabel buildLabel(final String text, final Font font, final Rectangle bounds) {
        final JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        lbl.setHorizontalTextPosition(SwingConstants.LEFT);
        lbl.setVerticalTextPosition(SwingConstants.TOP);
        if (bounds != null) {
            lbl.setBounds(bounds);
        }
        return lbl;
    }

    /**
     * Builds a {@code JLabel} with the given properties.
     *
     * @param text the text which will be shown on the label
     * @param tooltip the label's tool tip
     * @param hAligment the horizontal alignment
     * @param txtColor the text color
     * @param font the font of the label's text
     * @return a new {@code JLabel} object
     */
    public static JLabel buildLabel(final String text, final String tooltip, final int hAligment, final Color txtColor,
            final Font font) {
        final JLabel lbl = buildLabel(text, font, null);
        if (tooltip != null) {
            lbl.setToolTipText(tooltip);
        }
        lbl.setHorizontalAlignment(hAligment);
        if (txtColor != null) {
            lbl.setForeground(txtColor);
        }
        lbl.setFont(font);
        return lbl;
    }

    /***
     * Builds a {@code JList} with the given components. The created list will be horizontal wrapped and supports
     * multiple interval selection.
     *
     * @param data the data to be added into the list
     * @param renderer a custom list cell renderer
     * @param selection a list of elements to be selected
     * @return a {@code JList} object
     */
    public static <T> JList<T> buildList(final List<T> data, final DefaultListCellRenderer renderer,
            final List<T> selection) {
        final DefaultListModel<T> model = new DefaultListModel<>();
        for (final T elem : data) {
            model.addElement(elem);
        }
        final JList<T> list = new JList<>(model);
        list.setFont(FontUtil.PLAIN_12);
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
                    list.scrollRectToVisible(list.getCellBounds(list.getMinSelectionIndex(),
                            list.getMaxSelectionIndex()));
                }
            }
        });
        return list;
    }

    /**
     * Builds a {@code JMenuItem} with the given arguments.
     *
     * @param text the text to be displayed
     * @param icon the icon to be displayed
     * @param tooltip the tool tip to be displayed on mouse hover
     * @param listener the {@code MouseListener} associated with this item
     * @param enabled if true the item is enabled, if false it is disabled
     * @return a {@code JMenuItem}
     */
    public static JMenuItem buildMenuItem(final Icon icon, final String text, final String tooltip,
            final MouseListener listener, final boolean enabled) {
        final JMenuItem menuItem = new JMenuItem(text, icon);
        menuItem.setToolTipText(tooltip);
        if (enabled) {
            menuItem.addMouseListener(listener);
        }
        menuItem.setEnabled(enabled);
        return menuItem;
    }

    /**
     * Builds a {@code JRadioButton} with the given arguments.
     *
     * @param text the text to be displayed
     * @param font the font to be used
     * @param bgColor the background color
     * @return a {@code JRadioButton}
     */
    public static JRadioButton buildRadioButton(final String text, final Font font, final Color bgColor) {
        final JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBackground(bgColor);
        radioButton.setFont(font);
        radioButton.setFocusable(false);
        return radioButton;
    }

    /**
     * Builds a {@code JScrollPane} with the given arguments. The scroll pane scroll bars are displayed as needed.
     *
     * @param component the {@code Component} to be added to the scroll pane
     * @param bgColor the background color
     * @param borderVisible if true the scroll pane is created with a black border
     * @return a {@code JScrollPane} objects
     */
    public static JScrollPane buildScrollPane(final Component component, final Color bgColor,
            final boolean borderVisible) {
        final JScrollPane scrollPane = buildScrollPane(component, bgColor);
        if (borderVisible) {
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray));
        }
        return scrollPane;
    }

    /**
     * Builds a {@code JScrollPane} object with the given properties.
     *
     * @param name the name of the scroll pane
     * @param component the component to added into the scroll pane
     * @param bgColor the background color of the scroll pane
     * @param prefSize the preferred size of the component
     * @return a {@code JScrollPane} object
     */
    public static JScrollPane buildScrollPane(final String name, final Component component, final Color bgColor,
            final Dimension prefSize) {
        final JScrollPane scrollPane = buildScrollPane(component, bgColor);
        if (name != null) {
            scrollPane.setName(name);
        }
        scrollPane.setPreferredSize(prefSize);
        return scrollPane;
    }

    /**
     * Builds a {@code JTextArea} with the given arguments.
     *
     * @param txt the text to be displayed in the text component
     * @param editable specifies if the text can be edited or not
     * @param font the text's font
     * @param bgColor the background color
     * @param bounds the the dimension and location of the label
     * @return a {@code JTextArea}
     */
    public static JTextArea buildTextArea(final String txt, final boolean editable, final Font font,
            final Color bgColor, final Rectangle bounds) {
        JTextArea txtArea = null;
        if (txt != null) {
            txtArea = new JTextArea(txt);
        } else {
            txtArea = new JTextArea();
        }
        txtArea.setBackground(bgColor);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        txtArea.setFont(font);
        txtArea.setEditable(editable);
        if (bounds != null) {
            txtArea.setBounds(bounds);
        }
        return txtArea;
    }

    /**
     * Builds a {@code JTextField} with the given arguments.
     *
     * @param txt the text to be displayed
     * @param tooltip the tool tip to be displayed on mouse hover action
     * @param bgColor the background color
     * @return a {@code JTextField} object
     */
    public static JTextField buildTextField(final String txt, final String tooltip, final Color bgColor) {
        final JTextField txtField = new JTextField();
        if (txt != null) {
            txtField.setText(txt);
        }
        if (tooltip != null) {
            txtField.setToolTipText(tooltip);
        }
        txtField.setFont(FontUtil.PLAIN_12);
        txtField.setBackground(bgColor);
        return txtField;
    }

    /**
     * Builds a {@code JTextPane} with the given text and content type.
     *
     * @param txt the text to be displayed in the text component
     * @param contentType the text's content type
     * @return a {@code JTextPane}
     */
    public static JTextPane buildTextPane(final String txt, final String contentType) {
        final JTextPane txtPane = new JTextPane();
        txtPane.setCaretPosition(0);
        txtPane.setEditable(false);
        txtPane.setContentType(contentType);
        txtPane.setText(txt);
        return txtPane;
    }

    private static JScrollPane buildScrollPane(final Component component, final Color bgColor) {
        final JScrollPane scrollPane =
                new JScrollPane(component, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(bgColor);
        scrollPane.setAutoscrolls(true);
        return scrollPane;
    }

    private Builder() {}
}