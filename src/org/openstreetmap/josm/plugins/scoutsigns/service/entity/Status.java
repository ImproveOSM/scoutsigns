package org.openstreetmap.josm.plugins.scoutsigns.service.entity;


/**
 * Represents the status information contained in each FCDSearchService response.
 *
 * @author Beata
 * @version $Revision$
 */
public class Status {

    private static final int SUCCESS_CODE = 200;

    private final Integer apiCode;
    private final String apiMessage;
    private final Integer httpCode;
    private final String httpMessage;


    /**
     * Builds a new {@code Status} object with the given arguments.
     *
     * @param apiCode represents the FCDSearchService API code
     * @param apiMessage represents the FCDSearchService API message
     * @param httpCode represents the HTTP code
     * @param httpMessage represents the HTTP message associated with the HTTP code
     */
    public Status(final Integer apiCode, final String apiMessage, final Integer httpCode, final String httpMessage) {
        this.apiCode = apiCode;
        this.apiMessage = apiMessage;
        this.httpCode = httpCode;
        this.httpMessage = httpMessage;
    }


    public Integer getApiCode() {
        return apiCode;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    /**
     * Verifies if the status has or not a HTTP error code.
     *
     * @return true if the status has an error code, false otherwise.
     */
    public boolean isErrorCode() {
        return httpCode != SUCCESS_CODE;
    }
}