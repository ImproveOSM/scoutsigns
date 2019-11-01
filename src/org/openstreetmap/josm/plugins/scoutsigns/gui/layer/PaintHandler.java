/*
 * Copyright 2019 Grabtaxi Holdings PTE LTE (GRAB), All rights reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be found in the LICENSE file.
 *
 */
package org.openstreetmap.josm.plugins.scoutsigns.gui.layer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSignCluster;
import org.openstreetmap.josm.plugins.scoutsigns.gui.TypeIconFactory;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ClusterIconConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconConfig;
import com.telenav.josm.common.entity.Coordinate;
import com.telenav.josm.common.entity.Pair;
import com.telenav.josm.common.gui.PaintManager;
import com.telenav.josm.common.util.GeometryUtil;


/**
 * Handles the drawing operations of the layer data.
 *
 * @author Beata
 * @version $Revision$
 */
class PaintHandler {

    private static final Composite COMP = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);

    // trip view related constants
    private static final int ARROW_FREQ = 2;
    private static final int RADIUS = 15;
    private static final BasicStroke LINE_STROKE = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
    private static final Composite POS_COMP = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.60f);
    private static final int HEADING = 180;

    private final TypeIconFactory iconFactory = TypeIconFactory.getInstance();


    /**
     * Draws the given road signs clusters to the map. A road sign cluster is represented by an icon and the road sign
     * count drawn in the middle of the icon.
     *
     * @param g2D the {@code Graphics2D} used to draw
     * @param mv the current {@code MapView}
     * @param clusterList the list of {@code RoadSignCluster}s to be drawn
     */
    void drawRoadSignClusters(final Graphics2D g2D, final MapView mv, final List<RoadSignCluster> clusterList) {
        for (final RoadSignCluster cluster : clusterList) {
            final Point point = mv.getPoint(cluster.getPosition());
            final Pair<ImageIcon, Float> pair = ClusterIconConfig.getInstance().getIcon(cluster.getCount());
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pair.getSecond()));
            PaintManager.drawIcon(g2D, pair.getFirst(), point);
        }
        g2D.setComposite(COMP);
    }

    /**
     * Draws the given road signs to the map. A road sign is represented by an icon corresponding to its type. The
     * selected road signs are highlighted.
     *
     * @param g2D the {@code Graphics2D} used to draw
     * @param mv the current {@code MapView}
     * @param roadSignList the list of {@code RoadSign}s
     * @param selRoadSignList the list of selected {@code RoadSigns}
     */
    void drawRoadSigns(final Graphics2D g2D, final MapView mv, final List<RoadSign> roadSignList,
            final List<RoadSign> selRoadSignList) {
        for (final RoadSign roadSign : roadSignList) {
            final boolean selected = selRoadSignList.contains(roadSign);
            drawRoadSign(g2D, mv, roadSign, selected);
        }
    }

    private void drawRoadSign(final Graphics2D g2D, final MapView mv, final RoadSign roadSign, final boolean selected) {
        final Point point = mv.getPoint(roadSign.getSignPos().getPosition());
        if (mv.contains(point)) {
            if (selected) {
                PaintManager.drawIcon(g2D, IconConfig.getInstance().getSelRoadSignBgIcon(), point);
            }
            PaintManager.drawIcon(g2D, iconFactory.getIcon(roadSign.getType()), point);
        }
    }

    /**
     * Draws the given road sign along with the trip data (nearby positions) to the map.
     *
     * @param g2D the {@code Graphics2D} used to draw
     * @param mv the current {@code MapView}
     * @param roadSign the {@code RoadSign} to be drawn
     */
    void drawTripData(final Graphics2D g2D, final MapView mv, final RoadSign roadSign) {
        // draw positions
        g2D.setComposite(POS_COMP);
        if (roadSign.getNearbyPos() != null) {
            LatLon prevLatLon = roadSign.getNearbyPos().get(0);
            for (int i = 1; i < roadSign.getNearbyPos().size(); i++) {
                g2D.setColor(Color.black);
                g2D.setStroke(LINE_STROKE);
                final LatLon latLon = roadSign.getNearbyPos().get(i);
                if (!prevLatLon.equals(latLon) && !prevLatLon.equals(roadSign.getCarPos().getPosition())) {
                    if (i % ARROW_FREQ == 0) {
                        if (roadSign.getCarPos().getHeading() < HEADING) {
                            // forward
                            final Pair<Pair<Point, Point>, Pair<Point, Point>> arrowGeometry =
                                    getArrowGeometry(mv, prevLatLon, latLon, 2);
                            PaintManager.drawDirectedLine(g2D, new Pair<>(mv.getPoint(prevLatLon), mv.getPoint(latLon)),
                                    arrowGeometry);
                        } else {
                            final Pair<Pair<Point, Point>, Pair<Point, Point>> arrowGeometry =
                                    getArrowGeometry(mv, latLon, prevLatLon, 2);
                            PaintManager.drawDirectedLine(g2D, new Pair<>(mv.getPoint(prevLatLon), mv.getPoint(latLon)),
                                    arrowGeometry);
                        }
                    } else {
                        PaintManager.drawLine(g2D, new Pair<>(mv.getPoint(prevLatLon), mv.getPoint(latLon)));
                    }
                }
                PaintManager.drawCircle(g2D, mv.getPoint(latLon), Color.red, RADIUS);
                prevLatLon = latLon;
            }
            PaintManager.drawCircle(g2D, mv.getPoint(prevLatLon), Color.red, RADIUS);
        }
        g2D.setComposite(COMP);
        // draw road sign
        drawRoadSign(g2D, mv, roadSign, true);

    }

    private Pair<Pair<Point, Point>, Pair<Point, Point>> getArrowGeometry(final MapView mapView, final LatLon start,
            final LatLon end, final double length) {
        final LatLon midPoint = new LatLon((start.lat() + end.lat()) / 2, (start.lon() + end.lon()) / 2);
        final double bearing = Math.toDegrees(start.bearing(midPoint));
        final Pair<Coordinate, Coordinate> arrowEndCoordinates =
                GeometryUtil.arrowEndPoints(new Coordinate(midPoint.lat(), midPoint.lon()), bearing, -length);
        final Pair<Point, Point> arrowLine1 = new Pair<>(mapView.getPoint(midPoint), mapView.getPoint(
                new LatLon(arrowEndCoordinates.getFirst().getLat(), arrowEndCoordinates.getFirst().getLon())));
        final Pair<Point, Point> arrowLine2 = new Pair<>(mapView.getPoint(midPoint), mapView.getPoint(
                new LatLon(arrowEndCoordinates.getSecond().getLat(), arrowEndCoordinates.getSecond().getLon())));
        return new Pair<>(arrowLine1, arrowLine2);
    }
}