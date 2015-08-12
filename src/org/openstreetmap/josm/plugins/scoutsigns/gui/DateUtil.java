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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Utility class contains date methods used for formatting and parsing date values.
 *
 * @author Beata
 * @version $Revision$
 */
public final class DateUtil {

    private static final String MONTH = "MMMM yyyy";
    private static final String DAY = "MMM d, yyyy";
    private static final String TSTP = "yyyy-MM-dd HH:mm:ss";
    private static final Long UNIX_TSTP = 1000L;

    /**
     * Formats the given time using the following pattern: 'MMM d, yyyy'.
     *
     * @param time a {@ode Date} representing a given day
     * @return a {@code String}
     */
    public static String formatDay(final Date time) {
        final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DAY);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        return time != null ? dateTimeFormat.format(time) : "";
    }


    /**
     * Formats the given time using the following pattern: 'MMM d, yyyy'.
     *
     * @param time a {@code Long} value representing a time
     * @return a {@code String}
     */
    public static String formatDay(final Long time) {
        final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DAY);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        return time != null ? dateTimeFormat.format(time) : "";
    }

    /**
     * Formats the given month, using the following pattern:'MMMM yyyy'.
     *
     * @param timestamp a {@code Long} value representing a timestamp
     * @return a {@code String}
     */
    public static String formatMonth(final Long timestamp) {
        final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(MONTH);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        return timestamp != null ? dateTimeFormat.format(new Date(timestamp)) : "";
    }

    /**
     * Formats the given timestamp using the following pattern:'yyyy-MM-dd HH:mm:ss'.
     *
     * @param timestamp a {@code Long} value representing a timestamp
     * @return a {@code String}
     */
    public static String formatTimestamp(final Long timestamp) {
        final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(TSTP);
        dateTimeFormat.setTimeZone(TimeZone.getDefault());
        return timestamp != null ? dateTimeFormat.format(new Date(timestamp * UNIX_TSTP)) : "";
    }

    /**
     * Parses the given day and returns the corresponding date. The method returns null if the day argument is null or
     * empty.
     *
     * @param day a {@code String} representing a day
     * @return a {@code Date} object
     */
    public static Date parseDay(final String day) {
        Date result = null;
        if (day != null && !day.isEmpty()) {
            try {
                final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DAY);
                dateTimeFormat.setTimeZone(TimeZone.getDefault());
                result = dateTimeFormat.parse(day);
            } catch (final ParseException e) {
                // ignore it
            }
        }
        return result;
    }

    private DateUtil() {}
}