/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
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

    private Formatter() {}

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
}