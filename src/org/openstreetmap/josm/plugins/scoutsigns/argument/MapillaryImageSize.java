package org.openstreetmap.josm.plugins.scoutsigns.argument;


/**
 * Holds mapillary road sign image sizes.
 *
 * @author Beata
 */
public enum MapillaryImageSize {

    PX_320(320), PX_640(640), PX_1024(1024);

    private int value;


    private MapillaryImageSize(final int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "px";
    }
}