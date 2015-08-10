package org.openstreetmap.josm.plugins.scoutsigns.service.deserializer;

import java.lang.reflect.Type;
import org.openstreetmap.josm.data.coor.LatLon;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/**
 * Custom deserializer for the {@code LatLon} object.
 *
 * @author Beata
 * @version $Revision$
 */
public class LatLonDeserializer implements JsonDeserializer<LatLon> {

    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";


    @Override
    public LatLon deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject obj = (JsonObject) jsonElement;
        final double lat = obj.get(LATITUDE).getAsDouble();
        final double lon = obj.get(LONGITUDE).getAsDouble();
        return new LatLon(lat, lon);
    }
}