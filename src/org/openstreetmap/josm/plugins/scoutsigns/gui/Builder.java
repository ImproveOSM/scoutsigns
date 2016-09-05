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
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.telenav.josm.common.gui.GuiBuilder;


/**
 * Helper object, used for creating specific GUI elements.
 *
 * @author Bea
 * @version $Revision$
 */
public final class Builder {

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
        final JScrollPane scrollPane = GuiBuilder.buildScrollPane(component, bgColor,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        final JScrollPane scrollPane = GuiBuilder.buildScrollPane(component, bgColor,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        if (name != null) {
            scrollPane.setName(name);
        }
        scrollPane.setPreferredSize(prefSize);
        return scrollPane;
    }

    private Builder() {}
}