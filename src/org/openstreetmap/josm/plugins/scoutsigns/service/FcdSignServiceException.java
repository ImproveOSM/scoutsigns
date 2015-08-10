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