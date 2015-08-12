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
package org.openstreetmap.josm.plugins.scoutsigns.gui.layer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;
import java.util.List;
import javax.swing.ImageIcon;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSignCluster;
import org.openstreetmap.josm.plugins.scoutsigns.gui.TypeIconFactory;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.ClusterIconCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;
import org.openstreetmap.josm.tools.Pair;


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
    private static final double RADIUS = 15.0;
    private static final float ARROW_WIDTH = 6f;
    private static final BasicStroke LINE_STROKE = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
    private static final BasicStroke ARROW_STROKE = new BasicStroke(2f);
    private static final Composite POS_COMP = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.60f);
    private static final int HEADING = 180;
    private static final double PHI = Math.toRadians(55);

    private final TypeIconFactory iconFactory = TypeIconFactory.getInstance();


    private void drawArrow(final Graphics2D g2D, final Point tip, final Point tail) {
        final double theta = Math.atan2((tip.getY() - tail.getY()), (tip.getX() - tail.getX()));
        double rho = theta + PHI;
        g2D.setStroke(ARROW_STROKE);
        for (int j = 0; j < 2; j++) {
            g2D.draw(new Line2D.Double(tip.getX(), tip.getY(), tip.getX() - ARROW_WIDTH * Math.cos(rho),
                    tip.getY() - ARROW_WIDTH * Math.sin(rho)));
            rho = theta - PHI;
        }
    }

    private void drawCircle(final Graphics2D g2D, final Point point, final Color color, final Double radius) {
        final Ellipse2D.Double circle =
                new Ellipse2D.Double(point.x - radius / 2, point.y - radius / 2, radius, radius);
        g2D.setColor(color);
        g2D.fill(circle);
        g2D.draw(circle);
    }

    private void drawIcon(final Graphics2D g2D, final ImageIcon icon, final Point p) {
        g2D.drawImage(icon.getImage(), p.x - (icon.getIconWidth() / 2), p.y - (icon.getIconHeight() / 2),
                new ImageObserver() {

            @Override
            public boolean imageUpdate(final Image img, final int infoflags, final int x, final int y,
                    final int width, final int height) {
                return false;
            }
        });
    }


    private void drawLine(final Graphics2D g2D, final Point start, final Point end, final boolean forward,
            final boolean drawArrow) {
        g2D.setColor(Color.black);
        g2D.setStroke(LINE_STROKE);
        // draw line
        g2D.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
        if (drawArrow) {
            final Point midPoint = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
            if (forward) {
                drawArrow(g2D, midPoint, end);
            } else {
                drawArrow(g2D, midPoint, start);
            }
        }
    }

    private void drawRoadSign(final Graphics2D g2D, final MapView mv, final RoadSign roadSign, final boolean selected) {
        final Point point = mv.getPoint(roadSign.getSignPos().getPosition());
        if (mv.contains(point)) {
            if (selected) {
                drawIcon(g2D, IconCnf.getInstance().getSelRoadSignBgIcon(), point);
            }
            drawIcon(g2D, iconFactory.getIcon(roadSign.getSource(), roadSign.getType()), point);
        }
    }

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
            final Pair<ImageIcon, Float> pair = ClusterIconCnf.getInstance().getIcon(cluster.getCount());
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pair.b));
            drawIcon(g2D, pair.a, point);
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

    /**
     * Draws the given road sign along with the trip data (nearby positions) to the map.
     *
     * @param g2D the {@code Graphics2D} used to draw
     * @param mv the current {@code MapView}
     * @param roadSign the {@code RoadSign} to be drawn
     */
    void drawTripData(final Graphics2D g2D, final MapView mv, final RoadSign roadSign) {
        // draw road sign
        drawRoadSign(g2D, mv, roadSign, true);

        // draw positions
        g2D.setComposite(POS_COMP);
        if (roadSign.getNearbyPos() != null) {
            Point prevPoint = mv.getPoint(roadSign.getNearbyPos().get(0));
            for (int i = 1; i < roadSign.getNearbyPos().size(); i++) {
                final Point point = mv.getPoint(roadSign.getNearbyPos().get(i));
                final Boolean direction = roadSign.getCarPos().getHeading() < HEADING;
                if (!prevPoint.equals(point)) {
                    final boolean drawArrow = i % ARROW_FREQ == 0;
                    drawLine(g2D, prevPoint, point, direction, drawArrow);
                }
                drawCircle(g2D, prevPoint, Color.red, RADIUS);
                prevPoint = point;
            }
            drawCircle(g2D, prevPoint, Color.red, RADIUS);
        }
        g2D.setComposite(COMP);
    }
}