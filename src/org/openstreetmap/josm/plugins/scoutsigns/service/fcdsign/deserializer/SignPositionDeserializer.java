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
package org.openstreetmap.josm.plugins.scoutsigns.service.fcdsign.deserializer;

import java.lang.reflect.Type;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.SignPosition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/**
 * Custom deserializer for the {@code SignPosition} object.
 *
 * @author Beata
 * @version $Revision$
 */
public class SignPositionDeserializer implements JsonDeserializer<SignPosition> {

    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String HEIGHT = "height";


    @Override
    public SignPosition deserialize(final JsonElement jsonElement, final Type type,
            final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject obj = (JsonObject) jsonElement;
        final double lat = obj.get(LATITUDE).getAsDouble();
        final double lon = obj.get(LONGITUDE).getAsDouble();

        // height is null for searchSign responses
        final JsonElement heightElement = obj.get(HEIGHT);
        final Double height = heightElement != null ? heightElement.getAsDouble() : null;
        return new SignPosition(new LatLon(lat, lon), height);
    }
}