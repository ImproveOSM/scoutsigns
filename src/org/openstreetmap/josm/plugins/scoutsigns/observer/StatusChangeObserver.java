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
package org.openstreetmap.josm.plugins.scoutsigns.observer;

import org.openstreetmap.josm.plugins.scoutsigns.entity.Status;


/**
 * Observes the road sign status change user action.
 *
 * @author Beata
 * @version $Revision$
 */
public interface StatusChangeObserver {

    /**
     * Creates a new comment for the selected road sign. If the status argument is not null, then the road sign's status
     * is also changed.
     *
     * @param username the user's OSM username
     * @param text a comment justifying the user's action
     * @param status the road sign's new {@code Status}
     * @param duplicateOf the identifier of the road signs who's duplicate is the selected road sign
     */
    void statusChanged(String username, String text, Status status, Long duplicateOf);
}