/*
 * Copyright (c) 2015 SKOBBLER SRL.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SKOBBLER SRL
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with SKOBBLER SRL.
 *
 * Created on Mar 11, 2015 by Beata
 * Modified on $Date$
 *          by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.service.mapillary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.SignPosition;
import org.openstreetmap.josm.plugins.scoutsigns.service.mapillary.entities.MImage;
import org.openstreetmap.josm.plugins.scoutsigns.service.mapillary.entities.MRectangle;
import org.openstreetmap.josm.plugins.scoutsigns.service.mapillary.entities.MRoot;
import org.openstreetmap.josm.plugins.scoutsigns.util.cnf.TypeConfig;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpConnector;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpException;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;


/**
 * Provides access to the Mapillary API.
 *
 * @author Beata
 * @version $Revision$
 */
public class MapillaryService {

    static final int SCORE_DIV = 100;
    private final Gson gson;
    private final HttpQueryBuilder queryBuilder;


    /**
     * Builds a new {@code MapillaryManager}
     */
    public MapillaryService() {
        this.gson = new GsonBuilder().create();
        this.queryBuilder = new HttpQueryBuilder();
    }


    /**
     * Searches for road signs from the given area that fulfills the specified filters if any.
     *
     * @param bbox a {@code BoundingBox} specifying the searching area
     * @param filterPack a {@code FilterPack} a collection of filters which restrict the results
     * @return a collection of {@code RoadSign} objects
     * @throws MapillaryException if the operation failed
     */
    public Collection<RoadSign> searchSigns(final BoundingBox bbox, final SearchFilter filter)
            throws MapillaryServiceException {
        List<String> types = null;

        // enhance types
        SearchFilter newFilter = null;
        Double confidence = null;
        if (filter != null) {
            if (filter.getConfidence() != null) {
                confidence = (filter.getConfidence() / SCORE_DIV);
            }
            if (filter.getTypes() != null) {
                types = new ArrayList<>();
                for (final String type : filter.getTypes()) {
                    types.add(TypeConfig.getInstance().getMapillaryType(type));
                }
            }
            newFilter = new SearchFilter(filter.getTimestampFilter(), types, confidence);
        }
        final String url = queryBuilder.buildSearchQuery(bbox, newFilter);
        final MRoot root = executeGet(url);
        return buildRoadSignCollection(root.getIms(), types, confidence);
    }

    private MRoot buildMRoot(final String response) throws MapillaryServiceException {
        MRoot root = null;
        if (response != null) {
            try {
                root = gson.fromJson(response, MRoot.class);
                if (root.getCode() != null) {
                    throw new MapillaryServiceException(root.getMessage());
                }
            } catch (final JsonSyntaxException ex) {
                throw new MapillaryServiceException(ex);
            }
        }
        return root;
    }

    private Collection<RoadSign> buildRoadSignCollection(final Collection<MImage> input, final List<String> types,
            final Double confidence) {
        final Collection<RoadSign> output = new ArrayList<RoadSign>();
        for (final MImage mImg : input) {
            if (mImg.getRects() != null) {
                final SignPosition position = new SignPosition(new LatLon(mImg.getLat(), mImg.getLon()), null);
                for (final MRectangle mRect : mImg.getRects()) {
                    if (((types == null || types.isEmpty()) || types.contains(mRect.getType()))
                            && (confidence == null || mRect.getScore() >= confidence)) {
                        // remove extra road signs
                        final Short conf = (short) (mRect.getScore() * SCORE_DIV);
                        output.add(new RoadSign(mImg.getKey(), mRect.getType(), position, conf));
                    }
                }
            }
        }
        return output;
    }

    private MRoot executeGet(final String url) throws MapillaryServiceException {
        String response = null;
        try {
            response = new HttpConnector(url, HttpMethod.GET).read();
        } catch (final HttpException ex) {
            throw new MapillaryServiceException(ex);
        }
        return buildMRoot(response);
    }
}