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