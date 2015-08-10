package org.openstreetmap.josm.plugins.scoutsigns.util.pref;


/**
 * Holds the preference variable identifiers. Based on these keys the plugin values are saved/loaded to/from the
 * preference file.
 *
 * @author Beata
 * @version $Revision$
 */
public final class Keys {

    static final String ERROR_SUPPRESS = "scoutsigns.error.suppress";

    static final String CLUSTER_INFO_SUPPRESS = "scoutsigns.cluster.info.suppress";
    static final String MAPILLARY_INFO_SUPPRESS = "scoutsigns.mapillary.info.suppress";
    public static final String FILTERS_CHANGED = "scoutsigns.filter.changed";
    static final String SOURCE = "scoutsigns.filter.source";
    static final String FROM = "scoutsigns.filter.from";
    static final String TO = "scoutsigns.filter.to";
    static final String STATUS = "scoutsigns.filter.status";
    static final String TYPE = "scoutsigns.filter.types";
    static final String DUPLICATE = "scoutsigns.filter.duplicate";
    static final String CONFIDENCE = "scoutsigns.filter.confidence";
    static final String APP_NAME = "scoutsigns.filter.appName";
    static final String APP_VERSION = "scoutsigns.filter.appVersion";
    static final String OS_NAME = "scoutsigns.filter.osName";
    static final String OS_VERSION = "scoutsigns.filter.osVersion";
    static final String FLT_USERNAME = "scoutsigns.filter.username";
    static final String OSM_USERNAME = "osm-server.username";

    private Keys() {}
}