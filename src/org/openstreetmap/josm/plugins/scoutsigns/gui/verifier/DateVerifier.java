package org.openstreetmap.josm.plugins.scoutsigns.gui.verifier;

import java.util.Date;
import javax.swing.JComponent;
import org.openstreetmap.josm.plugins.scoutsigns.gui.DateUtil;


/**
 * Input verifier for the date text field.
 *
 * @author Beata
 * @version $Revision$
 */
public class DateVerifier extends AbstractVerifier {

    /**
     * Builds a new {@code DateVerifier} with the given arguments.
     *
     * @param component the {@code JComponent} that is validated
     * @param message a {@code String} to be displayed if the user input is invalid. This string is displayed as a
     * tool-tip.
     */
    public DateVerifier(final JComponent component, final String message) {
        super(component, message);
    }


    @Override
    protected boolean validate(final String value) {
        boolean valid = true;
        if (!value.isEmpty()) {
            final Date date = DateUtil.parseDay(value);
            if (date == null) {
                valid = false;
            }
        }
        return valid;
    }
}