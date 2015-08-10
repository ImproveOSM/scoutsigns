package org.openstreetmap.josm.plugins.scoutsigns.entity;

import org.openstreetmap.josm.data.coor.LatLon;


/**
 * Defines the 'car position' business entity. A car position represents the geographic position of the vehicle at the
 * time of the road sign creation.
 *
 * @author Beata
 * @version $Revision$
 */
public class CarPosition {

    private final LatLon position;
    private final Integer heading;
    private final Integer accuracy;
    private final String type;


    /**
     * Builds a new object with the given arguments.
     *
     * @param position the vehicle geographic position
     * @param heading the direction the vehicle was facing
     * @param accuracy the accuracy of the latitude and longitude in meters
     * @param type the type of the position (the interpolated GPS position or the last known GPS position)
     */
    public CarPosition(final LatLon position, final Integer heading, final Integer accuracy, final String type) {
        this.position = position;
        this.heading = heading;
        this.accuracy = accuracy;
        this.type = type;
    }


    public Integer getAccuracy() {
        return accuracy;
    }

    public Integer getHeading() {
        return heading;
    }

    public LatLon getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }
}