/*
 * Copyright (c) 2014 SKOBBLER SRL.
 * Cuza Voda 1, Cluj-Napoca, Cluj, 400107, Romania
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with SKOBBLER SRL.
 *
 * Created on Jul 29, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.text.DecimalFormat;
import java.util.Collection;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Comment;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;


/**
 * Utility class, formats custom objects.
 *
 * @author Bea
 * @version $Revision$
 */
public final class Formatter {

    /**
     * Defines the decimal formats.
     *
     * @author Beata
     * @version $Revision$
     */
    public enum DecFormat {
        SHORT("0.00"), LONG("0.000000");

        private String value;


        private DecFormat(final String value) {
            this.value = value;
        }


        public String getValue() {
            return value;
        }
    }


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
     * Formats the given decimal value, using the specified format.
     *
     * @param value the value to be formated
     * @param format specifies the format
     * @return a string containing the given value
     */
    public static String formatDecimal(final double value, final DecFormat format) {
        return new DecimalFormat(format.getValue()).format(value);
    }

    /**
     * Formats the given {@code LatLon} object. Returns a string of the following format: (lat, lon).
     *
     * @param point a {@code LatLon} to be formatted
     * @return a string containing the given {@code LatLon}
     */
    public static String formatLatLon(final LatLon point) {
        final StringBuilder sb = new StringBuilder();
        sb.append("(").append(formatDecimal(point.lat(), DecFormat.LONG));
        sb.append("; ");
        sb.append(formatDecimal(point.lon(), DecFormat.LONG)).append(")");
        return sb.toString();
    }

    private static String formatComment(final Comment value) {
        final StringBuilder sb = new StringBuilder("<b>");
        sb.append(DateUtil.formatTimestamp(value.getTstamp()));
        sb.append(", ").append(value.getUsername());
        sb.append("</b><br>");
        if (value.getStatus() != null) {
            sb.append("changed status to ");
            if (value.getStatus() == Status.SOLVED) {
                sb.append(value.getStatus());
            } else if (value.getStatus() == Status.DUPLICATE) {
                sb.append(value.getStatus());
                sb.append("(").append(value.getDuplicateOf()).append(")");
            } else {
                sb.append(value.getStatus());
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