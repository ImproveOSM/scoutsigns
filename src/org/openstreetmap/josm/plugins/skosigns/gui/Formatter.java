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
package org.openstreetmap.josm.plugins.skosigns.gui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.skosigns.entity.Comment;
import org.openstreetmap.josm.plugins.skosigns.entity.Status;


/**
 * Utility class, formats custom objects.
 * 
 * @author Bea
 * @version $Revision$
 */
public final class Formatter {
    
    private static final String TSTP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private Formatter() {}
    
    
    /**
     * Formats the given collection of {@code Comment}s using html tags. 
     * 
     * @param comments a collection of {@code Comment}s
     * @return a string containing the given {@code Comment}s
     */
    public static String formatComments(Collection<Comment> comments) {
        StringBuilder sb = new StringBuilder(
                "<html><font size='3' face='times new roman'>");
        for (Comment comment : comments) {
            sb.append("<p>");
            sb.append(formatComment(comment));
            sb.append("</p>");
        }
        sb.append("</font></html>");
        return sb.toString();
    }
    
    private static String formatComment(Comment value) {
        StringBuilder sb = new StringBuilder("<b>");
        sb.append(Formatter.formatTimestamp(value.getTstamp()));
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
    
    /**
     * Formats the given {@code LatLon} object. Returns a string of the
     * following format: (lat, lon).
     * 
     * @param point a {@code LatLon} to be formatted
     * @return a string containing the given {@code LatLon}
     */
    public static String formatLatLon(LatLon point) {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(formatDecimal(point.lat(), DecFormat.LONG));
        sb.append("; ");
        sb.append(formatDecimal(point.lon(), DecFormat.LONG)).append(")");
        return sb.toString();
    }
    
    /**
     * Formats the given decimal value, using the specified format.
     * 
     * @param value the value to be formated
     * @param format specifies the format 
     * @return a string containing the given value
     */
    public static String formatDecimal(double value, DecFormat format) {
        return new DecimalFormat(format.getValue()).format(value);
    }
    
    /**
     * Formats the given timestamp using the default time zone. Returns a string
     * of the following format: yyyy-MM-dd HH:mm:ss.
     * 
     * @param timestamp a long representing a timestamp
     * @return a formated timestamp
     */
    public static String formatTimestamp(Long timestamp) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(TSTP_FORMAT);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        Date date = new Date(timestamp);
        return date != null ? dateTimeFormat.format(date) : "";
    }
    
    
    /**
     * Defines the decimal formats.
     * 
     * @author Beata
     * @version $Revision$
     */
    public enum DecFormat {
        SHORT("0.00"), LONG("0.000000");
        
        private String value;
        
        
        private DecFormat(String value) {
            this.value = value;
        }
        
        
        public String getValue() {
            return value;
        }
    }
}