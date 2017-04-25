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

import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import com.telenav.josm.common.formatter.DateFormatter;
import com.telenav.josm.common.formatter.DecimalPattern;
import com.telenav.josm.common.formatter.EntityFormatter;


/**
 * Utility class, formats custom objects.
 *
 * @author Bea
 * @version $Revision$
 */
public final class Formatter {

    /**
     * Formats the given {@code LatLon} object. Returns a string of the following format: (lat, lon).
     *
     * @param point a {@code LatLon} to be formatted
     * @return a string containing the given {@code LatLon}
     */
    public static String formatLatLon(final LatLon point) {
        final StringBuilder sb = new StringBuilder();
        sb.append("(").append(EntityFormatter.formatDouble(point.lat(), false, DecimalPattern.LONG));
        sb.append("; ");
        sb.append(EntityFormatter.formatDouble(point.lon(), false, DecimalPattern.LONG)).append(")");
        return sb.toString();
    }

    public static String formatComment(final Comment value) {
        final StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<b>");
        sb.append(DateFormatter.formatTimestamp(value.getTstamp()));
        sb.append(", ").append(value.getUsername());
        sb.append("</b><br>");
        if (value.getStatus() != null) {
            sb.append("changed status to ");
            sb.append(value.getStatus());
            if (value.getStatus() == Status.DUPLICATE) {
                sb.append("(").append(value.getDuplicateOf()).append(")");
            }
            sb.append("<br>").append("with ");
        } else {
            sb.append("added ");
        }
        sb.append("comment: ").append(value.getText());
        sb.append("</body></html>");
        return sb.toString();
    }


    private Formatter() {}
}