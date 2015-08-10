package org.openstreetmap.josm.plugins.scoutsigns.util.retry;


/**
 * Defines the retry setup for a given method.
 *
 * @author Beata
 * @version $Revision$
 */
public class RetrySetup {

    /** The default configuration */
    public static final RetrySetup DEFAULT = new RetrySetup(1, 500);

    private final int stopCondition;
    private final int baseDelay;


    /**
     * Builds a new {@code RetrySetup} object with the specified values.
     *
     * @param stopCondition the condition for stopping the attempts
     * @param baseDelay the delay time between the attempts
     */
    public RetrySetup(final int stopCondition, final int baseDelay) {
        this.stopCondition = stopCondition;
        this.baseDelay = baseDelay;
    }

    /**
     * Returns the base delay in milliseconds between attempts.
     *
     * @return an integer representing the base delay
     */
    public int getBaseDelay() {
        return baseDelay;
    }

    /**
     * Returns the value at which new attempts are ceased. This value it is interpreted as the
     * "maximum number of attempts".
     *
     * @return an integer representing the stop condition
     */
    public int getStopCondition() {
        return stopCondition;
    }
}