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
package org.openstreetmap.josm.plugins.scoutsigns.service;


/**
 * Custom exception used by the {@code FcdSignService} object.
 *
 * @author Beata
 * @version $Revision$
 */
public class FcdSignServiceException extends Exception {

    private static final long serialVersionUID = -1210836364186862213L;


    /**
     * Builds a new {@code FcdSignServiceException} with he given message.
     *
     * @param message the message of the exception
     */
    public FcdSignServiceException(final String message) {
        super(message);
    }

    /**
     * Builds a new {@code FcdSignServiceException} with he given message and cause.
     *
     * @param message the message of the exception
     * @param cause the cause of the exception
     */
    public FcdSignServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Builds a new {@code FcdSignServiceException} with he given cause.
     *
     * @param cause the cause of the exception
     */
    public FcdSignServiceException(final Throwable cause) {
        super(cause);
    }
}