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
package org.openstreetmap.josm.plugins.skosigns.entity;


/**
 * Defines the trip business entity.
 * 
 * @author Beata
 * @version $Revision$
 */
public class Trip {
    
    private String id;
    private String mode;
    private String profile;
    private Application app;
    private Device device;
    
    
    /**
     * Builds a new object with the given arguments.
     * 
     * @param id the unique trip identifier
     * @param mode the driving mode in which the trip has been obtained
     * @param profile the profile of the routing
     * @param app the {@code Application} object, contains information related
     * to the used application
     * @param device the {@code Device} object, contains information related to
     * the used application
     */
    public Trip(String id, String mode, String profile, Application app,
            Device device) {
        this.id = id;
        this.mode = mode;
        this.profile = profile;
        this.app = app;
        this.device = device;
    }
    
    
    public String getId() {
        return id;
    }
    
    public String getMode() {
        return mode;
    }
    
    public String getProfile() {
        return profile;
    }
    
    public Application getApp() {
        return app;
    }
    
    public Device getDevice() {
        return device;
    }
}