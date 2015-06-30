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
 * Created on Jul 16, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.entity;

import java.util.Collection;
import java.util.List;
import org.openstreetmap.josm.data.coor.LatLon;


/**
 * Defines the attributes of a road sign.
 *
 * @author Beata
 * @version $Revision$
 */
public class RoadSign {

    private final Long id;
    private final String type;
    private final Source source;
    private final Long tstamp;
    private final Image image;
    private final Short confidence;
    private final CarPosition carPos;
    private final SignPosition signPos;
    private final List<LatLon> nearbyPos;
    private final Trip trip;
    private final Status status;
    private final Long duplicateOf;
    private final Collection<Comment> comments;

    // the identifier of the image, this property is used for external road
    // signs
    private final String key;


    /**
     * Builds a new object with the given arguments.
     *
     * @param id the road sign's unique identifier
     * @param type the road sign's type
     * @param source the road sign's origin
     * @param tstamp the road sign's creation time in Unix time format
     * @param image a {@code Image} represents the road sign's picture
     * @param confidence the confidence with which the sign has been recognized (0-100)
     * @param carPos a {@code CarPosition} object representing the geographic position of the vehicle at the time of the
     * road sign creation
     * @param signPos a {@code SignPosition} object, representing the geographic position of the road sign
     * @param nearbyPos a collection of {@code Position} objects, representing the geographical positions of the car
     * after and before the road sign
     * @param trip a {@code Trip} object, representing a meta-data about the trip during which the road sign was created
     * @param status a {@code Status} object, representing the road sign's status
     * @param duplicateOf the id of the road sign of which this road sign is a duplicate of. This information is present
     * only if the road sign has the {@code Status#DUPLICATE} value.
     * @param comments a collection of {@code Comment} objects, representing the comments posted by the users related to
     * the road sign
     * @param key represents the road sign's image identifier; this property is used only for external road signs
     */
    public RoadSign(final Long id, final String type, final Source source, final Long tstamp, final Image image,
            final Short confidence, final CarPosition carPos, final SignPosition signPos, final List<LatLon> nearbyPos,
            final Trip trip, final Status status, final Long duplicateOf, final Collection<Comment> comments,
            final String key) {
        this.id = id;
        this.type = type;
        this.source = source;
        this.signPos = signPos;
        this.status = status;
        this.tstamp = tstamp;
        this.image = image;
        this.confidence = confidence;
        this.carPos = carPos;
        this.nearbyPos = nearbyPos;
        this.trip = trip;
        this.duplicateOf = duplicateOf;
        this.comments = comments;
        this.key = key;
    }


    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof RoadSign) {
            final RoadSign other = (RoadSign) obj;

            if (id != null) {
                result = id.equals(other.getId());
            } else {
                // id is null for external signs
                result = ObjectUtil.bothNullOrEqual(type, other.getType());
                result = result && ObjectUtil.bothNullOrEqual(signPos, other.getSignPos());
                result = result && ObjectUtil.bothNullOrEqual(confidence, other.getConfidence());
                result = result && ObjectUtil.bothNullOrEqual(key, other.getKey());
            }
        }
        return result;
    }

    public CarPosition getCarPos() {
        return carPos;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public Short getConfidence() {
        return confidence;
    }

    public Long getDuplicateOf() {
        return duplicateOf;
    }

    public Long getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public String getKey() {
        return key;
    }

    public List<LatLon> getNearbyPos() {
        return nearbyPos;
    }

    public SignPosition getSignPos() {
        return signPos;
    }

    public Source getSource() {
        return source;
    }

    public Status getStatus() {
        return status;
    }

    public Trip getTrip() {
        return trip;
    }

    public Long getTstamp() {
        return tstamp;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if (id != null) {
            result = prime * result + id.hashCode();
        } else {
            result = prime * result + ObjectUtil.hashCode(type);
            result = prime * result + ObjectUtil.hashCode(signPos);
            result = prime * result + ObjectUtil.hashCode(confidence);
            result = prime * result + ObjectUtil.hashCode(key);
        }
        return result;
    }
}