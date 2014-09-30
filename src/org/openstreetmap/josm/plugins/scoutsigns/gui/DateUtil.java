/*
 * Copyright (c) 2014, skobbler GmbH
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
 * Created on Sep 24, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Utility class contains date methods used for formatting and parsing date
 * values.
 * 
 * @author Beata
 * @version $Revision$
 */
public final class DateUtil {
    
    private static final Long TSTP_VAL = 1000L;
    private static final String MONTH = "MMMM yyyy";
    private static final String DAY = "MMM d, yyyy";
    private static final String TSTP = "yyyy-MM-dd HH:mm:ss";
    
    private DateUtil() {}
    
    
    /**
     * Formats the given timestamp using the following pattern:'yyyy-MM-dd
     * HH:mm:ss'.
     * 
     * @param timestamp a {@code Long} value representing a timestamp
     * @return a {@code String}
     */
    public static String formatTimestamp(Long timestamp) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(TSTP);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        Date date = new Date(timestamp * TSTP_VAL);
        return date != null ? dateTimeFormat.format(date) : "";
    }
    
    /**
     * Formats the given month, using the following pattern:'MMMM yyyy'.
     * 
     * @param timestamp a {@code Long} value representing a timestamp
     * @return a {@code String}
     */
    public static String formatMonth(Long timestamp) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(MONTH);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        Date date = new Date(timestamp);
        return date != null ? dateTimeFormat.format(date) : "";
    }
    
    /**
     * Formats the given time using the following pattern: 'MMM d, yyyy'.
     * 
     * @param time a {@ode Date} representing a given day
     * @return a {@code String}
     */
    public static String formatDay(Date time) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DAY);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        return time != null ? dateTimeFormat.format(time) : "";
    }
    
    /**
     * Formats the given time using the following pattern: 'MMM d, yyyy'.
     * 
     * @param time a {@code Long} value representing a time
     * @return a {@code String}
     */
    public static String formatDay(Long time) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DAY);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        return time != null ? dateTimeFormat.format(time) : "";
    }
    
    /**
     * Parses the given day and returns the corresponding date. The method
     * returns null if the day argument is null or empty.
     * 
     * @param day a {@code String} representing a day
     * @return a {@code Date} object
     */
    public static Date parseDay(String day) {
        Date result = null;
        if (day != null && !day.isEmpty()) {
            try {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DAY);
                dateTimeFormat.setTimeZone(TimeZone.getDefault());
                result = dateTimeFormat.parse(day);
            } catch (ParseException e) {
                result = null;
            }
        }
        return result;
    }
}