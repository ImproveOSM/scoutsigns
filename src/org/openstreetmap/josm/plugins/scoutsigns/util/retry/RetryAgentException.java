package org.openstreetmap.josm.plugins.scoutsigns.util.retry;


/**
 *
 * @author Beata
 * @version $Revision$
 */
public class RetryAgentException extends Exception {

    private static final long serialVersionUID = 2618369008732779091L;

    /**
     * Builds an empty {@code RetryAgentException} object.
     */
    public RetryAgentException() {}

    /**
     * Builds a new {@code RetryAgentException} object with the given message.
     *
     * @param msg the message of the exception
     */
    public RetryAgentException(final String msg) {
        super(msg);
    }

    /**
     * Builds a new {@code RetryAgentException} object with the given message and cause.
     *
     * @param msg the message of the exception
     * @param cause the cause of the exception
     */
    public RetryAgentException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Builds a new {@code RetryAgentException} object with the given cause.
     *
     * @param cause the cause of the exception
     */
    public RetryAgentException(final Throwable cause) {
        super(cause);
    }
}