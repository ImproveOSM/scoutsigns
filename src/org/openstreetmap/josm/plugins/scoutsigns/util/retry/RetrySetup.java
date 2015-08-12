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