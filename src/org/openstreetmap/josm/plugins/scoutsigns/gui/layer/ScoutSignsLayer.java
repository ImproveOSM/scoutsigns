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

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.Icon;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.dialogs.LayerListDialog;
import org.openstreetmap.josm.gui.dialogs.LayerListPopup;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.scoutsigns.entity.DataSet;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.util.Util;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.GuiCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.IconCnf;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TltCnf;


/**
 * Defines the ScoutSigns layer main functionality.
 *
 * @author Bea
 * @version $Revision$
 */
public class ScoutSignsLayer extends Layer {

    private final PaintHandler paintHandler;
    private DataSet dataSet;
    private final List<RoadSign> selRoadSigns;

    private boolean tripView;


    /**
     * Builds a new {@code SkoSignsLayer} with default functionality.
     */
    public ScoutSignsLayer() {
        super(GuiCnf.getInstance().getDlgDetailsTitle());
        paintHandler = new PaintHandler();
        selRoadSigns = new ArrayList<>();
    }

    @Override
    public Icon getIcon() {
        return IconCnf.getInstance().getLayerIcon();
    }

    @Override
    public Object getInfoComponent() {
        return TltCnf.getInstance().getLayerInfo();
    }

    @Override
    public Action[] getMenuEntries() {
        final LayerListDialog layerListDlg = LayerListDialog.getInstance();
        return new Action[] { layerListDlg.createActivateLayerAction(this), layerListDlg.createShowHideLayerAction(),
                layerListDlg.createDeleteLayerAction(), SeparatorLayerAction.INSTANCE,
                new LayerListPopup.InfoAction(this) };
    }

    /**
     * Returns the selected road signs. If no road sign(s) is selected the method return an empty list.
     *
     * @return a list of {@code RoadSign}s
     */
    public List<RoadSign> getSelRoadSigns() {
        return selRoadSigns;
    }

    @Override
    public String getToolTipText() {
        return TltCnf.getInstance().getPluginTlt();
    }

    @Override
    public boolean isMergable(final Layer layer) {
        return false;
    }

    public boolean isTripView() {
        return tripView;
    }

    /**
     * Return the last selected road sign. If no road sign is selected the method returns null.
     *
     * @return a {@code RoadSign} object
     */
    public RoadSign lastSelRoadSign() {
        return selRoadSigns.isEmpty() ? null : selRoadSigns.get(selRoadSigns.size() - 1);
    }

    @Override
    public void mergeFrom(final Layer layer) {
        // merge operation is not supported
    }

    /**
     * Returns the road sign near to the given point. The method returns null if there is no nearby road sign.
     *
     * @param point a {@code Point}
     * @param multiSelect specifies if multiple elements are selected or not
     * @return a {@code RoadSign}
     */
    public RoadSign nearbyRoadSign(final Point point, final boolean multiSelect) {
        final RoadSign roadSign = dataSet != null ? Util.nearbyRoadSign(dataSet.getRoadSigns(), point) : null;
        if (!multiSelect) {
            selRoadSigns.clear();
        }
        if (roadSign != null && !selRoadSigns.contains(roadSign)) {
            selRoadSigns.add(roadSign);
        }
        return roadSign;
    }

    @Override
    public void paint(final Graphics2D g2D, final MapView mv, final Bounds bounds) {
        mv.setDoubleBuffered(true);
        if (tripView) {
            // draw selected road sign's trip
            paintHandler.drawTripData(g2D, mv, selRoadSigns.get(0));
        } else if (dataSet != null) {

            if (!dataSet.getRoadSigns().isEmpty()) {
                // draw road signs
                paintHandler.drawRoadSigns(g2D, mv, dataSet.getRoadSigns(), selRoadSigns);
            } else if (!dataSet.getRoadSignClusters().isEmpty()) {
                // draw road sign clusters
                paintHandler.drawRoadSignClusters(g2D, mv, dataSet.getRoadSignClusters());
            }
        }
    }

    /**
     * Sets the layer's data set. Previously selected road signs will be unselected if the new data set does not contain
     * these elements.
     *
     * @param dataSet a {@code DataSet} containing road signs from the current view
     */
    public void setDataSet(final DataSet dataSet) {
        this.dataSet = dataSet;
        if (!selRoadSigns.isEmpty() && dataSet.getRoadSigns() != null) {
            for (final RoadSign elem : dataSet.getRoadSigns()) {
                if (!this.dataSet.getRoadSigns().contains(elem)) {
                    selRoadSigns.remove(elem);
                }
            }
        }
    }

    public void setTripView(final boolean tripView) {
        this.tripView = tripView;
    }

    /**
     * Updates the currently selected road sign data.
     *
     * @param roadSign a {@code RoadSign} representing the selected object
     */
    public void updateSelRoadSign(final RoadSign roadSign) {
        final int idx = selRoadSigns.indexOf(roadSign);
        if (idx > -1) {
            selRoadSigns.remove(roadSign);
            selRoadSigns.add(idx, roadSign);
        }
    }

    @Override
    public void visitBoundingBox(final BoundingXYVisitor arg0) {
        // not supported
    }
}