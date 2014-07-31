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
package org.openstreetmap.josm.plugins.skosigns.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.openstreetmap.josm.plugins.skosigns.argument.BoundingBox;
import org.openstreetmap.josm.plugins.skosigns.argument.SearchFilter;
import org.openstreetmap.josm.plugins.skosigns.entity.RoadSign;
import org.openstreetmap.josm.plugins.skosigns.entity.SignPosition;
import org.openstreetmap.josm.plugins.skosigns.entity.Status;
import org.openstreetmap.josm.plugins.skosigns.service.deserializer.SignPositionDeserializer;
import org.openstreetmap.josm.plugins.skosigns.service.deserializer.StatusDeserializer;
import org.openstreetmap.josm.plugins.skosigns.service.entity.Root;
import org.openstreetmap.josm.plugins.skosigns.util.http.HttpConnector;
import org.openstreetmap.josm.plugins.skosigns.util.http.HttpException;
import org.openstreetmap.josm.plugins.skosigns.util.http.HttpMethod;
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
    
    private Gson gson;
    
    
    /**
     * Builds a new {@code FcdSignService} object.
     */
    public FcdSignService() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SignPosition.class,
                new SignPositionDeserializer());
        builder.registerTypeAdapter(Status.class, new StatusDeserializer());
        this.gson = builder.create();
    }
    
    /**
     * Searches for the road signs from the specified area that satisfy the given
     * filters. Null filters are ignored.
     * 
     * @param bbox a {@code BoundingBox} specifies the searching area
     * @param filter specifies the search filters
     * @param zoom the current zoom level
     * @return a collection of {@code RoadSign}. If no road signs are found, the
     * method returns an empty collection.
     * @throws FcdSignServiceException if an error occurred during the 
     * FcdSignService method execution 
     */
    public Collection<RoadSign> searchSigns(BoundingBox bbox,
            SearchFilter filter, int zoom) throws FcdSignServiceException {
        String url = new HttpQueryBuilder(bbox, filter, zoom).build(
                Constants.SEARCH_SIGNS);
        Root root = executeGet(url);
        if (root.getStatus() != null && root.getStatus().isErrorCode()) {
            throw new FcdSignServiceException(
                    "Error occured durring searchSigns method execution:"
                            + root.getStatus().getApiMessage());
        }
        return root.getRoadSigns() != null ? root.getRoadSigns()
                : new ArrayList<RoadSign>();
    }
    
    /* this will be used by addComment/addComments methods */
    @SuppressWarnings("unused")
    private Root executePost(String url, Map<String, String> content)
            throws FcdSignServiceException {
        String response = null;
        try {
            HttpConnector connector = new HttpConnector(url, HttpMethod.POST);
            connector.write(content);
            response = connector.read();
        } catch (HttpException ex) {
            throw new FcdSignServiceException(ex);
        }
        return buildRoot(response);
    }
    
    private Root executeGet(String url) throws FcdSignServiceException {
        String response = null;
        try {
            response = new HttpConnector(url, HttpMethod.GET).read();
        } catch (HttpException ex) {
            throw new FcdSignServiceException(ex);
        }
        return buildRoot(response);
    }
    
    private Root buildRoot(String response) throws FcdSignServiceException {
        Root root = null;
        if (response != null) {
            try {
                root = gson.fromJson(response, Root.class);
            } catch (JsonSyntaxException ex) {
                throw new FcdSignServiceException(ex);
            }
        }
        return root;
    }
}