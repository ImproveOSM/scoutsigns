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