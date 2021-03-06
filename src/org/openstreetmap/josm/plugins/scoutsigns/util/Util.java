/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.util;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import com.telenav.josm.common.argument.BoundingBox;


/**
 * Provides utility methods.
 *
 * @author Beata
 * @version $Revision$
 */
public final class Util {

    private static final double POZ_DIST = 15.0;

    private Util() {}

    /**
     * Computes the bounding box of the currently visible {@code MapView}.
     *
     * @param mapView the currently visible map view
     * @return a {@code BoundingBox} object
     */
    public static BoundingBox buildBBox(final MapView mapView) {
        final Bounds bounds =
                new Bounds(mapView.getLatLon(0, mapView.getHeight()), mapView.getLatLon(mapView.getWidth(), 0));
        return new BoundingBox(bounds.getMax().lat(), bounds.getMin().lat(), bounds.getMax().lon(),
                bounds.getMin().lon());
    }

    /**
     * Returns the nearest road sign to the given point. If there is no nearest road sign the method returns null.
     *
     * @param roadSigns a collection of {@code RoadSign}s
     * @param point a {@code Point} representing a location
     * @return the corresponding {@code RoadSing}
     */
    public static RoadSign nearbyRoadSign(final Collection<RoadSign> roadSigns, final Point point) {
        double minDist = Double.MAX_VALUE;
        RoadSign result = null;
        if (roadSigns != null) {
            for (final RoadSign roadSign : roadSigns) {
                final double dist = distance(point, roadSign.getSignPos().getPosition());
                if (dist <= minDist && dist <= POZ_DIST) {
                    minDist = dist;
                    result = roadSign;
                }
            }
        }
        return result;
    }

    private static double distance(final Point2D fromPoint, final LatLon toLatLon) {
        final Point toPoint = MainApplication.getMap().mapView.getPoint(toLatLon);
        return new Point2D.Double(fromPoint.getX(), fromPoint.getY()).distance(toPoint);
    }

}