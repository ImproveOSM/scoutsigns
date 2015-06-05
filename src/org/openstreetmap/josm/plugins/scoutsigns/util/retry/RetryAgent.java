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
package org.openstreetmap.josm.plugins.scoutsigns.util.retry;


/**
 * A retry agent attempts several times to execute a given operation.
 *
 * @author Beata
 * @version $Revision$
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