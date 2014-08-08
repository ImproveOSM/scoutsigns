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
 * Created on Jul 30, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.skosigns.util;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.skosigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.skosigns.entity.RoadSign;


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
    public static BoundingBox buildBBox(MapView mapView) {
        Bounds bounds = new Bounds(mapView.getLatLon(0, mapView.getHeight()),
                mapView.getLatLon(mapView.getWidth(), 0));
        return new BoundingBox(bounds.getMax().lat(), bounds.getMin().lat(),
                bounds.getMax().lon(), bounds.getMin().lon());
    }
    
    /**
     * Returns the nearest road sign to the given point. If there is no nearest
     * road sign the method returns null.
     * 
     * @param roadSigns a collection of {@code RoadSign}s
     * @param point a {@code Point} representing a location
     * @return the corresponding {@code RoadSing}
     */
    public static RoadSign nearbyRoadSign(Collection<RoadSign> roadSigns,
            Point point) {
        double minDist = Double.MAX_VALUE;
        RoadSign result = null;
        if (roadSigns != null) {
            for (RoadSign roadSign : roadSigns) {
                double dist =
                        distance(point, roadSign.getSignPos().getPosition());
                if (dist <= minDist && dist <= POZ_DIST) {
                    minDist = dist;
                    result = roadSign;
                }
            }
        }
        return result;
    }
    
    private static double distance(Point2D fromPoint, LatLon toLatLon) {
        Point toPoint = Main.map.mapView.getPoint(toLatLon);
        return new Point2D.Double(fromPoint.getX(), fromPoint.getY())
                .distance(toPoint);
    }
}