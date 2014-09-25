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
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
     * Builds a custom {@code JLabel} with the given properties.
     * 
     * @param text the text which will be shown on the label
     * @param font the font of the label's text
     * @param bounds the dimension and location of the label
     * @return a new {@code JLabel} object
     */
    public static JLabel buildLabel(String text, Font font, Rectangle bounds) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        label.setVerticalTextPosition(SwingConstants.TOP);
        if (bounds != null) {
            label.setBounds(bounds);
        }
        return label;
    }
    
    /**
     * Builds a custom {@code JLabel} with the given properties.
     * 
     * @param icon the {@code Icon} to be displayed on the label
     * @param bounds the dimension and location of the label
     * @return a {@code JLabel}
     */
    public static JLabel buildLabel(Icon icon, Rectangle bounds) {
        JLabel label = new JLabel(icon);
        if (bounds != null) {
            label.setBounds(bounds);
        }
        return label;
    }
    
    public static JLabel buildLabel(String text, String tooltip, int hAligment,
            Color textColor, Font font) {
        JLabel label = new JLabel(text);
        if (tooltip != null) {
            label.setToolTipText(tooltip);
        }
        label.setHorizontalAlignment(hAligment);
        if (textColor != null) {
            label.setForeground(textColor);
        }
        label.setFont(font);
        return label;
    }
    
    /**
     * Builds a custom {@code JScrollPane} object with the given properties.
     * 
     * @param name the name of the scroll pane
     * @param component the component to added into the scroll pane
     * @param bgColor the background color of the scroll pane
     * @param prefSize the preferred size of the component
     * @return a {@code JScrollPane} object
     */
    public static JScrollPane buildScrollPane(String name, Component component,
            Color bgColor, Dimension prefSize) {
        JScrollPane pane =
                new JScrollPane(component,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBackground(bgColor);
        pane.setAutoscrolls(true);
        if (name != null) {
            pane.setName(name);
        }
        pane.setPreferredSize(prefSize);
        return pane;
    }
    
    /**
     * Builds a custom {@code JButton} object with the given properties.
     * 
     * @param action the {@code AbstractAction} to be executed when the button
     * is clicked
     * @param icon the {@code Icon} to be displayed on the button
     * @return a {@code JButton}
     */
    public static JButton buildButton(AbstractAction action, Icon icon) {
        JButton button;
        if (action == null) {
            button = new JButton();
        } else {
            button = new JButton(action);
        }
        button.setIcon(icon);
        button.setFocusable(false);
        return button;
    }
    
    /**
     * Builds a custom {@code JTextPane} with the given text and content type.
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
     * Builds a new {@code JTextArea} with the given arguments.
     * 
     * @param txt the text to be displayed in the text component
     * @param font the text's font
     * @param bgColor the background color
     * @param bounds the the dimension and location of the label
     * @return a {@code JTextArea}
     */
    public static JTextArea buildTextArea(String txt, Font font, Color bgColor,
            Rectangle bounds) {
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
        txtArea.setEditable(false);
        if (bounds != null) {
            txtArea.setBounds(bounds);
        }
        return txtArea;
    }
    
    public static JCheckBox buildCheckBox(String tooltip, boolean selected) {
        JCheckBox cbbox = new JCheckBox();
        cbbox.setToolTipText(tooltip);
        cbbox.setFont(FontUtil.PLAIN_12);
        cbbox.setFocusPainted(false);
        cbbox.setSelected(selected);
        return cbbox;
    }
    
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
    
    public static JScrollPane buildScrollPane(Component component, Color bgColor) {
        JScrollPane scrollPane =
                new JScrollPane(component,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(bgColor);
        scrollPane.setAutoscrolls(true);
        return scrollPane;
    }
    
    public static JButton buildButton(AbstractAction action, String text) {
        JButton button = new JButton(action);
        button.setFont(FontUtil.BOLD_12);
        button.setText(text);
        button.setFocusable(false);
        return button;
    }
    
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