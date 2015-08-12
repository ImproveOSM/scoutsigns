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
 * A retry agent attempts several times to execute a given operation.
 *
 * @author Beata
 * @version $Revision$
 * @param <T> the object type to be retried
 */
public abstract class RetryAgent<T> {

    private static final int MULT = 3;
    private static final int DIV = 2;

    private RetrySetup setup;


    /**
     * Builds a new retry agent with default setup.
     */
    public RetryAgent() {
        this(RetrySetup.DEFAULT);
    }

    /**
     * Builds a new retry agent with the given setup
     *
     * @param setup the setup for this {@code RetryAgent}
     */
    public RetryAgent(final RetrySetup setup) {
        this.setup = setup;
    }


    /**
     * Launches the {@code RetryAgent}'s execution. The target and cleanup operations will run at least once.
     *
     * @return an object of type T, defined by the implementor
     * @throws RetryAgentException in case the target method failed on every attempt and the running conditions have
     * been exhausted
     */
    public T run() throws RetryAgentException {
        T result = null;
        boolean success = false;
        int attempts = setup.getStopCondition();
        int delay = setup.getBaseDelay();
        do {
            attempts--;
            try {
                result = target();
                success = true;
            } catch (final Exception e) {
                if (attempts <= 0) {
                    throw new RetryAgentException(e);
                }
                pause(delay);
                delay = computeDelay(delay);
            } finally {
                cleanup();
            }
        } while (!success && attempts > 0);
        return result;
    }

    /**
     * The clean up operation of the retry agent. This method is called after each call of the target method.
     *
     * @throws RetryAgentException in the case if the cleanup operation fails
     */
    protected abstract void cleanup() throws RetryAgentException;

    /**
     * The target operation of the retry agent. This method is called several times, until it returns successfully, or
     * until the number of attempts has been exhausted.
     *
     * @return an object of type T, defined by implementor
     * @throws RetryAgentException in the case if the target operation fails
     */
    protected abstract T target() throws RetryAgentException;

    private int computeDelay(final int delay) {
        return delay * MULT / DIV;
    }

    private void pause(final int delay) throws RetryAgentException {
        try {
            Thread.sleep(delay);
        } catch (final InterruptedException e) {
            throw new RetryAgentException(e);
        }
    }
}