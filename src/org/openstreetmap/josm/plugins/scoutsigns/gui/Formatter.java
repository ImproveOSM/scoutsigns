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

import java.util.Collection;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import com.telenav.josm.common.formatter.DecFormat;
import com.telenav.josm.common.formatter.EntityFormatter;
import com.telenav.josm.common.util.DateUtil;


/**
 * Utility class, formats custom objects.
 *
 * @author Bea
 * @version $Revision$
 */
public final class Formatter {

    /**
     * Formats the given collection of {@code Comment}s using html tags.
     *
     * @param comments a collection of {@code Comment}s
     * @return a string containing the given {@code Comment}s
     */
    public static String formatComments(final Collection<Comment> comments) {
        final StringBuilder sb = new StringBuilder("<html><body><font size='3' face='times new roman'>");
        for (final Comment comment : comments) {
            sb.append(formatComment(comment));
            sb.append("<br>");
        }
        sb.append("</font></body></html>");
        return sb.toString();
    }

    /**
     * Formats the given {@code LatLon} object. Returns a string of the following format: (lat, lon).
     *
     * @param point a {@code LatLon} to be formatted
     * @return a string containing the given {@code LatLon}
     */
    public static String formatLatLon(final LatLon point) {
        final StringBuilder sb = new StringBuilder();
        sb.append("(").append(EntityFormatter.formatDouble(point.lat(), false, DecFormat.LONG));
        sb.append("; ");
        sb.append(EntityFormatter.formatDouble(point.lon(), false, DecFormat.LONG)).append(")");
        return sb.toString();
    }

    private static String formatComment(final Comment value) {
        final StringBuilder sb = new StringBuilder("<b>");
        sb.append(DateUtil.formatTimestamp(value.getTstamp()));
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
        return sb.toString();
    }


    private Formatter() {}
}