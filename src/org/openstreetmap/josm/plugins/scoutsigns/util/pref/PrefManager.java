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
package org.openstreetmap.josm.plugins.scoutsigns.util.pref;

import java.util.Collection;
import java.util.List;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.StructUtils;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.argument.TimestampFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Application;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Device;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;


/**
 * Utility class, manages save and load (put & get) operations of the preference variables. The preference variables are
 * saved into a global preference file. Preference variables are static variables which can be accessed from any plugin
 * class. Values saved in this global file, can be accessed also after a JOSM restart.
 *
 * @author Beata
 * @version $Revision$
 */
public final class PrefManager {

    private static final String NULL = "null";

    private static final PrefManager UNIQUE_INSTANCE = new PrefManager();


    /**
     * Returns the unique instance of the {@code PrefManager}.
     *
     * @return a {@code PrefManager}
     */
    public static PrefManager getInstance() {
        return UNIQUE_INSTANCE;
    }

    /**
     * Loads the OSM user name from the global preference file.
     *
     * @return a {@code String}
     */
    public String loadOsmUsername() {
        final String username = Main.pref.get(Keys.OSM_USERNAME);
        return username == null ? "" : username;
    }

    /**
     * Loads the search filter from the global preference file.
     *
     * @return a {@code SearchFilter} object
     */
    public SearchFilter loadSearchFilter() {
        final List<String> types = StructUtils.getListOfStructs(Main.pref, Keys.TYPE, String.class);
        final String statusStr = Main.pref.get(Keys.STATUS);
        final String confidenceStr = Main.pref.get(Keys.CONFIDENCE);
        final String username = Main.pref.get(Keys.FLT_USERNAME);
        final String appName = Main.pref.get(Keys.APP_NAME);
        final String appVersion = Main.pref.get(Keys.APP_VERSION);
        final String osName = Main.pref.get(Keys.OS_NAME);
        final String osVersion = Main.pref.get(Keys.OS_VERSION);

        final Long from = loadLongValue(Keys.FROM);
        final Long to = loadLongValue(Keys.TO);
        final Status status = (statusStr != null && !statusStr.isEmpty()) ? Status.valueOf(statusStr) : null;
        final Long duplicate = loadLongValue(Keys.DUPLICATE);

        Double confidence;
        if (confidenceStr.isEmpty()) {
            // never set
            confidence = SearchFilter.DEF_CONFIDENCE;
        } else if (confidenceStr.equals(NULL)) {
            confidence = null;
        } else {
            confidence = Double.valueOf(confidenceStr);
        }

        return new SearchFilter(new TimestampFilter(from, to), types, status, duplicate, confidence,
                new Application(appName, appVersion), new Device(osName, osVersion), username);
    }

    /**
     * Loads the suppress cluster info flag.
     *
     * @return a boolean value
     */
    public boolean loadSuppressClusterInfoFlag() {
        final String flagVal = Main.pref.get(Keys.CLUSTER_INFO_SUPPRESS);
        return flagVal.isEmpty() ? false : new Boolean(flagVal);
    }

    /**
     * Loads the suppress error flag.
     *
     * @return a boolean value
     */
    public boolean loadSupressErrorFlag() {
        return Main.pref.getBoolean(Keys.ERROR_SUPPRESS);
    }

    /**
     * Saves the "filters changed" flag in the global preference file.
     *
     * @param changed a boolean value
     */
    public void saveFiltersChangedFlag(final boolean changed) {
        Main.pref.put(Keys.FILTERS_CHANGED, "");
        Main.pref.put(Keys.FILTERS_CHANGED, "" + changed);
    }

    /**
     * Saves the OSM username in the global preference file.
     *
     * @param username a {@code String}
     */
    public void saveOsmUsername(final String username) {
        Main.pref.put(Keys.OSM_USERNAME, username);
    }

    /**
     * Saves the given search filter to the global preference file.
     *
     * @param filter the {@code SearchFilter} object
     */
    public void saveSearchFilter(final SearchFilter filter) {
        String from = "";
        String to = "";
        String status = "";
        String duplicate = "";
        String confidence = "";
        String username = "";
        String appName = "";
        String appVersion = "";
        String osName = "";
        String osVersion = "";
        Collection<String> types = null;

        if (filter != null) {
            if (filter.getTimestampFilter() != null) {
                final TimestampFilter tstpFilter = filter.getTimestampFilter();
                from = tstpFilter.getFrom() != null ? tstpFilter.getFrom().toString() : "";
                to = tstpFilter.getTo() != null ? tstpFilter.getTo().toString() : "";
            }
            types = filter.getTypes();
            status = filter.getStatus() != null ? filter.getStatus().name() : null;
            duplicate = filter.getDuplicateOf() != null ? filter.getDuplicateOf().toString() : null;
            confidence = filter.getConfidence() != null ? filter.getConfidence().toString() : NULL;
            username = filter.getUsername();
            if (filter.getApp() != null) {
                appName = filter.getApp().getName();
                appVersion = filter.getApp().getVersion();
            }
            if (filter.getDevice() != null) {
                osName = filter.getDevice().getOsName();
                osVersion = filter.getDevice().getOsVersion();
            }
        }
        // clear collection is no types is set
        Main.pref.put(Keys.FROM, from);
        Main.pref.put(Keys.TO, to);
        Main.pref.put(Keys.STATUS, status);
        StructUtils.putListOfStructs(Main.pref, Keys.TYPE, types, String.class);
        Main.pref.put(Keys.DUPLICATE, duplicate);
        Main.pref.put(Keys.CONFIDENCE, confidence);
        Main.pref.put(Keys.FLT_USERNAME, username);
        Main.pref.put(Keys.APP_NAME, appName);
        Main.pref.put(Keys.APP_VERSION, appVersion);
        Main.pref.put(Keys.OS_NAME, osName);
        Main.pref.put(Keys.OS_VERSION, osVersion);
    }

    /**
     * Saves the given value to the global preference file. Based on this value an info message is displayed to the user
     * when entering the clustered view.
     *
     * @param value a boolean value
     */
    public void saveSuppressClusterInfoFlag(final boolean value) {
        Main.pref.putBoolean(Keys.CLUSTER_INFO_SUPPRESS, value);
    }

    /**
     * Saves the given value to the global preference file. Based on this value an occurred error is shown or not to the
     * end user.
     *
     * @param value a boolean value
     */
    public void saveSupressErrorFlag(final boolean value) {
        Main.pref.putBoolean(Keys.ERROR_SUPPRESS, value);
    }

    private Long loadLongValue(final String key) {
        String valueStr = Main.pref.get(key);
        valueStr = valueStr.trim();
        return (valueStr != null && !valueStr.isEmpty()) ? Long.valueOf(valueStr) : null;
    }
}