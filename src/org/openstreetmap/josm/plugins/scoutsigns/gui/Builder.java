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
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


/**
 * Helper object, used for creating specific GUI elements.
 * 
 * @author Bea
 * @version $Revision$
 */
public final class Builder {
    
    private static final int HEADER_WIDTH = 12;
    
    private Builder() {}
    
    
    /**
     * Builds a {@code JLabel} with the given properties.
     * 
     * @param icon the {@code Icon} to be displayed on the label
     * @param bounds the dimension and location of the label
     * @return a {@code JLabel}
     */
    public static JLabel buildLabel(Icon icon, Rectangle bounds) {
        JLabel lbl = new JLabel(icon);
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
     * @param bounds the dimension and location of the label
     * @return a new {@code JLabel} object
     */
    public static JLabel buildLabel(String text, Font font, Rectangle bounds) {
        JLabel lbl = new JLabel(text);
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
     * @param textColor the text color
     * @param font the font of the label's text
     * @return
     */
    public static JLabel buildLabel(String text, String tooltip, int hAligment,
            Color txtColor, Font font) {
        JLabel lbl = buildLabel(text, font, null);
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
    
    /**
     * Builds a {@code JTextField} with the given arguments.
     * 
     * @param txt the text to be displayed
     * @param tooltip the tool tip to be displayed on mouse hover action
     * @param bgColor the background color
     * @return a {@code JTextField} object
     */
    public static JTextField buildTextField(String txt, String tooltip,
            Color bgColor) {
        JTextField txtField = new JTextField();
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
    public static JTextPane buildTextPane(String txt, String contentType) {
        JTextPane txtPane = new JTextPane();
        txtPane.setCaretPosition(0);
        txtPane.setEditable(false);
        txtPane.setContentType(contentType);
        txtPane.setText(txt);
        return txtPane;
    }
    
    /**
     * Builds a {@code JTextArea} with the given arguments.
     * 
     * @param txt the text to be displayed in the text component
     * @param font the text's font
     * @param bgColor the background color
     * @param bounds the the dimension and location of the label
     * @return a {@code JTextArea}
     */
    public static JTextArea buildTextArea(String txt, boolean editable,
            Font font, Color bgColor, Rectangle bounds) {
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
     * Builds a {@code JMenuItem} with the given arguments.
     * 
     * @param text the text to be displayed
     * @param icon the icon to be displayed
     * @param listener the {@code MouseListener} associated with this item
     * @param enabled if true the item is enabled, if false it is disabled
     * @return a {@code JMenuItem}
     */
    public static JMenuItem buildMenuItem(String text, Icon icon,
            MouseListener listener, boolean enabled) {
        JMenuItem menuItem = new JMenuItem(text, icon);
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
    public static JRadioButton buildRadioButton(String text, Font font, 
            Color bgColor) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBackground(bgColor);
        radioButton.setFont(font);
        radioButton.setFocusable(false);
        return radioButton;
    }
    
    /**
     * Builds a {@code JButton} with the given arguments.
     * 
     * @param action the action to be executed when the button is clicked
     * @param text the text to be displayed
     * @return a {@code JButton} object
     */
    public static JButton buildButton(AbstractAction action, String text) {
        JButton btn = new JButton(action);
        btn.setFont(FontUtil.BOLD_12);
        btn.setText(text);
        btn.setFocusable(false);
        return btn;
    }
    
    /**
     * Builds a {@code JButton} object with the given properties.
     * 
     * @param action the {@code AbstractAction} to be executed when the button
     * is clicked
     * @param icon the {@code Icon} to be displayed on the button
     * @return a {@code JButton}
     */
    public static JButton buildButton(AbstractAction action, Icon icon) {
        JButton btn;
        if (action == null) {
            btn = new JButton();
        } else {
            btn = new JButton(action);
        }
        btn.setIcon(icon);
        btn.setFocusable(false);
        return btn;
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
    public static JScrollPane buildScrollPane(String name, Component component,
            Color bgColor, Dimension prefSize) {
        JScrollPane scrollPane = buildScrollPane(component, bgColor);
        if (name != null) {
            scrollPane.setName(name);
        }
        scrollPane.setPreferredSize(prefSize);
        return scrollPane;
    }
    
    /**
     * Builds a {@code JScrollPane} with the given arguments. The scroll pane
     * scroll bars are displayed as needed.
     * 
     * @param component the {@code Component} to be added to the scroll pane
     * @param bgColor the background color
     * @param borderVisible if true the scroll pane is created with a black
     * border
     * @return a {@code JScrollPane} objects
     */
    public static JScrollPane buildScrollPane(Component component,
            Color bgColor, boolean borderVisible) {
        JScrollPane scrollPane = buildScrollPane(component, bgColor);
        if (borderVisible) {
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray));
        }
        return scrollPane;
    }
    
    private static JScrollPane buildScrollPane(Component component,
            Color bgColor) {
        JScrollPane scrollPane =
                new JScrollPane(component,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(bgColor);
        scrollPane.setAutoscrolls(true);
        return scrollPane;
    }
    
    /**
     * Builds a box layout {@code JPanel} with the given components.
     * 
     * @param cmpLeft the left component
     * @param cmpCenter the center component
     * @param cmpRight the right component
     * @return a {@code JPanel} object
     */
    public static JPanel buildBoxLayoutPanel(JComponent cmpLeft,
            JComponent cmpCenter, JComponent cmpRight) {
        JPanel panel = new JPanel();
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
}