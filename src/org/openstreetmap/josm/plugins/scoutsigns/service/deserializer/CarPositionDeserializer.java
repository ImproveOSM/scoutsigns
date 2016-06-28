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
package org.openstreetmap.josm.plugins.scoutsigns.service.deserializer;

import java.lang.reflect.Type;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.plugins.scoutsigns.entity.CarPosition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/**
 * Custom deserializer for the {@code CarPosition} object.
 *
 * @author Beata
 * @version $Revision$
 */
public class CarPositionDeserializer implements JsonDeserializer<CarPosition> {

    private static final String HEADING = "heading";
    private static final String ACCURACY = "accuracy";
    private static final String TYPE = "type";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";


    @Override
    public CarPosition deserialize(final JsonElement jsonElement, final Type type,
            final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject obj = (JsonObject) jsonElement;
        final int heading = obj.get(HEADING).getAsInt();
        final int accuracy = obj.get(ACCURACY).getAsInt();
        final JsonElement carType = obj.get(TYPE);
        String carPosType;
        if (carType != null) {
            carPosType = carType.getAsString();
        } else {
            carPosType = "";
        }
        final double lat = obj.get(LATITUDE).getAsDouble();
        final double lon = obj.get(LONGITUDE).getAsDouble();
        return new CarPosition(new LatLon(lat, lon), heading, accuracy, carPosType);
    }
}