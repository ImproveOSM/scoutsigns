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


/**
 * The observable interface for the {@code TripViewObserver} object.
 *
 * @author Beata
 * @version $Revision$
 */
public interface TripViewObservable {

    /**
     * Notifier the registered observers.
     *
     * @param enterView if true then the trip of the selected road sign will be displayed; if false then the road signs
     * from the current view will be displayed
     */
    void notifyObserver(boolean enterView);

    /**
     * Registers the given observer.
     *
     * @param observer a {@code TripViewObserver}
     */
    void registerObserver(TripViewObserver observer);
}