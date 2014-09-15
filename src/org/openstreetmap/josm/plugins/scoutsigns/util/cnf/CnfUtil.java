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
 * Created on Jun 17, 2014 by Bea
 * Modified on $DateTime$ by $Author$
 */
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * 
 * @author Bea
 * @version $Revision$
 */
final class CnfUtil {
    
    private static final String SEPARATOR = ";";
    
    private CnfUtil() {}
    
    
    /**
     * Loads the properties from the given file.
     * 
     * @param fileName the name of a properties file
     * @return a {@code Properties} object
     */
    static Properties load(String fileName) {
        Properties properties = new Properties();
        URL url = CnfUtil.class.getResource("/" + fileName);
        if (url == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError(
                    "Could not find configuration file:" + fileName);
        }
        try (InputStream stream = url.openStream()) {
            properties.load(stream);
        } catch (IOException e) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError(e);
        }
        return properties;
    }
    
    
    /**
     * Reads the property with the given key from the given properties.
     * 
     * @param properties a {@code Properties} object
     * @param key the key of a property
     * @return the value of the property
     */
    static String readProperty(Properties properties, String key) {
        return properties.getProperty(key);
    }
    
    static List<String> readPropertiesList(Properties properties, String key) {
        String[] values = readPropertiesArray(properties, key);
        return values != null ? Arrays.asList(values) : new ArrayList<String>();
    }
    
    static String[] readPropertiesArray(Properties properties, String key) {
        String values = properties.getProperty(key);
        return (values != null && !values.isEmpty()) ? values.split(SEPARATOR)
                : null;
    }
    
}