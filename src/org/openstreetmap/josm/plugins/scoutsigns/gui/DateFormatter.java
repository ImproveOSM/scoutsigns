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

import java.text.ParseException;
import java.util.Date;
import javax.swing.JFormattedTextField.AbstractFormatter;


/**
 * Date formatter for text fields.
 *
 * @author Beata
 * @version $Revision$
 */
public final class DateFormatter extends AbstractFormatter {

    private static final long serialVersionUID = -1900706805089814228L;


    @Override
    public Object stringToValue(final String text) {
        return DateUtil.parseDay(text);
    }

    @Override
    public String valueToString(final Object value) throws ParseException {
        return DateUtil.formatDay((Date) value);
    }
}