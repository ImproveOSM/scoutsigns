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
 * Created on Jul 29, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.service;

import java.util.List;
import java.util.Map;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.scoutsigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.scoutsigns.entity.CarPosition;
import org.openstreetmap.josm.plugins.scoutsigns.entity.DataSet;
import org.openstreetmap.josm.plugins.scoutsigns.entity.RoadSign;
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


/**
 * Executes the operations of the FcdSignService.
 *
 * @author Beata
 * @version $Revision$
 */
public class FcdSignService {

    private final Gson gson;


    /**
     * Builds a new {@code FcdSignService} object.
     */
    public FcdSignService() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SignPosition.class, new SignPositionDeserializer());
        builder.registerTypeAdapter(CarPosition.class, new CarPositionDeserializer());
        builder.registerTypeAdapter(LatLon.class, new LatLonDeserializer());
        this.gson = builder.create();
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
    public void addComment(final Long signId, final String username, final String text, final Status status,
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
    public void addComments(final List<Long> signIds, final String username, final String text, final Status status,
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
    public RoadSign retrieveRoadSign(final Long id) throws FcdSignServiceException {
        final String url = new HttpQueryBuilder(id).build(Constants.RETRIEVE_SIGN);
        final Root root = executeGet(url);
        verifyStatus(root);
        return root.getRoadSign();
    }

    /**
     * Searches for the road signs from the specified area that satisfy the given filters. Null filters are ignored.
     *
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param filter specifies the search filters
     * @param zoom the current zoom level
     * @return a {@code DataSet} containing a list of road signs or a list of road sign clusters
     * @throws FcdSignServiceException if an error occurred during the FcdSignService method execution
     */
    public DataSet searchSigns(final BoundingBox bbox, final SearchFilter filter, final int zoom)
            throws FcdSignServiceException {
        final String url = new HttpQueryBuilder(bbox, filter, zoom).build(Constants.SEARCH_SIGNS);
        final Root root = executeGet(url);
        verifyStatus(root);
        return new DataSet(root.getRoadSigns(), root.getRoadSignClusters());
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
}