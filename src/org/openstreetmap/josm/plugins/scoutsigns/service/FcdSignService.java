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
package org.openstreetmap.josm.plugins.scoutsigns.service;

import java.util.List;
import java.util.Map;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.CarPosition;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSignCluster;
import org.openstreetmap.josm.plugins.scoutsigns.entity.SignPosition;
import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;
import org.openstreetmap.josm.plugins.scoutsigns.service.deserializer.CarPositionDeserializer;
import org.openstreetmap.josm.plugins.scoutsigns.service.deserializer.LatLonDeserializer;
import org.openstreetmap.josm.plugins.scoutsigns.service.deserializer.SignPositionDeserializer;
import org.openstreetmap.josm.plugins.scoutsigns.service.entity.Root;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpConnector;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpException;
import org.openstreetmap.josm.plugins.scoutsigns.util.http.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.telenav.josm.common.argument.BoundingBox;


/**
 * Executes the operations of the FcdSignService.
 *
 * @author Beata
 * @version $Revision$
 */
class FcdSignService {

    private final Gson gson;


    /**
     * Builds a new {@code FcdSignService} object.
     */
    FcdSignService() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SignPosition.class, new SignPositionDeserializer());
        builder.registerTypeAdapter(CarPosition.class, new CarPositionDeserializer());
        builder.registerTypeAdapter(LatLon.class, new LatLonDeserializer());
        this.gson = builder.create();
    }


    private Root buildRoot(final String response) throws FcdSignServiceException {
        Root root = new Root();
        if (response != null) {
            try {
                root = gson.fromJson(response, Root.class);
            } catch (final JsonSyntaxException ex) {
                throw new FcdSignServiceException(ex);
            }
        }
        return root;
    }

    private Root executeGet(final String url) throws FcdSignServiceException {
        String response = null;
        try {
            response = new HttpConnector(url, HttpMethod.GET).read();
        } catch (final HttpException ex) {
            throw new FcdSignServiceException(ex);
        }
        return buildRoot(response);
    }

    private Root executePost(final String url, final Map<String, String> content) throws FcdSignServiceException {
        String response = null;
        try {
            final HttpConnector connector = new HttpConnector(url, HttpMethod.POST);
            connector.write(content);
            response = connector.read();
        } catch (final HttpException ex) {
            throw new FcdSignServiceException(ex);
        }
        return buildRoot(response);
    }

    private void verifyStatus(final Root root) throws FcdSignServiceException {
        if (root.getStatus() != null && root.getStatus().isErrorCode()) {
            throw new FcdSignServiceException(root.getStatus().getApiMessage());
        }
    }

    /**
     * Adds a comment to the specified road sign. If the status is not null, then also the road sign's status is
     * modified.
     *
     * @param signId the road sign's identifier
     * @param username the user's OSM username
     * @param text the comment text
     * @param status the road sign's new {@code Status}
     * @param duplicateOf it is used only with {@code Status#DUPLICATE}. Specifies the parent road sign's identifier.
     * @throws FcdSignServiceException if an error occurred during the FcdSignService method execution
     */
    void addComment(final Long signId, final String username, final String text, final Status status,
            final Long duplicateOf) throws FcdSignServiceException {
        final Map<String, String> content =
                new HttpContentBuilder(signId, username, text, status, duplicateOf).getContent();
        final String url = new HttpQueryBuilder().build(Constants.ADD_COMMENT);
        final Root root = executePost(url, content);
        verifyStatus(root);
    }

    /**
     * Adds the same comment to every specified road sign. This is a batch ' operation, and is equivalent to calling
     * 'addComment' on each individual road sign. If the status is not null, then also the road sign's status is
     * modified.
     *
     * @param signIds the collection of road sign identifiers
     * @param username the user's OSM username
     * @param text the comment text
     * @param status the road sign's new {@code Status}
     * @param duplicateOf it is used only with {@code Status#DUPLICATE}. Specifies the parent road sign's identifier.
     * @throws FcdSignServiceException if an error occurred during the FcdSignService method execution
     */
    void addComments(final List<Long> signIds, final String username, final String text, final Status status,
            final Long duplicateOf) throws FcdSignServiceException {
        final Map<String, String> content =
                new HttpContentBuilder(signIds, username, text, status, duplicateOf).getContent();
        final String url = new HttpQueryBuilder().build(Constants.ADD_COMMENTS);
        final Root root = executePost(url, content);
        verifyStatus(root);
    }

    /**
     * Retrieves the road sign corresponding to the given identifier.
     *
     * @param id the identifier of the desired road sign
     * @return a {@code RoadSign} object
     * @throws FcdSignServiceException if an error occurred during the FcdSignService method execution
     */
    RoadSign retrieveRoadSign(final Long id) throws FcdSignServiceException {
        final String url = new HttpQueryBuilder(id).build(Constants.RETRIEVE_SIGN);
        final Root root = executeGet(url);
        verifyStatus(root);
        RoadSign roadSign = null;
        if (root.getRoadSign() != null) {
            roadSign = root.getRoadSign();
        }
        return roadSign;
    }

    /**
     * Searches for road sign clusters from the specified area.
     *
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param zoom the current zoom level
     * @return a list of {@code RoadSignCluster}s
     * @throws FcdSignServiceException if an error occurred during the FcdSignService method execution
     */
    List<RoadSignCluster> searchClusters(final BoundingBox bbox, final int zoom)
            throws FcdSignServiceException {
        final String url = new HttpQueryBuilder(bbox, zoom).build(Constants.SEARCH_SIGNS);
        final Root root = executeGet(url);
        verifyStatus(root);
        return root.getRoadSignClusters();
    }

    /**
     * Searches for the road signs from the specified area that satisfy the given filters. Null filters are ignored.
     *
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param filter specifies the search filters
     * @param zoom the current zoom level
     * @return a list of {@code RoadSign}s
     * @throws FcdSignServiceException if an error occurred during the FcdSignService method execution
     */
    List<RoadSign> searchSigns(final BoundingBox bbox, final SearchFilter filter, final int zoom)
            throws FcdSignServiceException {
        final String url = new HttpQueryBuilder(bbox, filter, zoom).build(Constants.SEARCH_SIGNS);
        final Root root = executeGet(url);
        verifyStatus(root);
        return root.getRoadSigns();
    }
}