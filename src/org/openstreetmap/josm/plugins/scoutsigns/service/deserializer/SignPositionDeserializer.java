package org.openstreetmap.josm.plugins.scoutsigns.service.deserializer;

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