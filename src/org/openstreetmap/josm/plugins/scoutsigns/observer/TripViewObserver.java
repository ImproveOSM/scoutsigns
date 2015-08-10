package org.openstreetmap.josm.plugins.scoutsigns.observer;


/**
 * Observes the road sign trip visualization user actions.
 *
 * @author Beata
 * @version $Revision$
 */
public interface TripViewObserver {

    /**
     * Displays the selected road sing's trip.
     */
    void enterTripView();

    /**
     * Exits the trip view and displays the road signs from the current view.
     */
    void exitTripView();
}