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
 * Created on Jul 29, 2014 by Beata
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.skosigns.service.entity;


/**
 * Represents the status information contained in each FCDSearchService
 * response.
 * 
 * @author Beata
 * @version $Revision$
 */
public class Status {
    
    private static final int CODE_400 = 400;
    private static final int CODE_404 = 404;
    
    private Integer apiCode;
    private String apiMessage;
    private Integer httpCode;
    private String httpMessage;
    
    
    /**
     * Builds a new {@code Status} object with the given arguments.
     * 
     * @param apiCode represents the FCDSearchService API code
     * @param apiMessage represents the FCDSearchService API message
     * @param httpCode represents the HTTP code
     * @param httpMessage represents the HTTP message associated with the HTTP
     * code
     */
    public Status(Integer apiCode, String apiMessage, Integer httpCode,
            String httpMessage) {
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
        return httpCode == CODE_400 || httpCode == CODE_404;
    }
}