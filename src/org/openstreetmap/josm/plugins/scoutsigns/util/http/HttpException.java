package org.openstreetmap.josm.plugins.scoutsigns.util.http;


/**
 * Custom exception used when there has been an exception in the HTTP communication.
 *
 * @author Beata
 * @version $Revision$
 */
public class HttpException extends Exception {

    private static final long serialVersionUID = 5983585806490844333L;

    /**
     * Builds a new object.
     */
    public HttpException() {}

    /**
     * Builds a new object with the given message.
     *
     * @param msg the message of the exception
     */
    public HttpException(final String msg) {
        super(msg);
    }

    /**
     * Builds a new object with the given cause and message.
     *
     * @param msg the message of the exception
     * @param cause the cause of the exception
     */
    public HttpException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * Builds a new object with the given cause.
     *
     * @param cause the cause of the exception
     */
    public HttpException(final Throwable cause) {
        super(cause);
    }
}