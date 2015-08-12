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
package org.openstreetmap.josm.plugins.scoutsigns.util.cnf;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * Utility class, defines basic methods for loading run time properties.
 *
 * @author Bea
 * @version $Revision$
 */
final class CnfUtil {

    private static final String SEPARATOR = ";";

    /**
     * Loads the properties from the given file.
     *
     * @param fileName the name of a properties file
     * @return a {@code Properties} object
     */
    static Properties load(final String fileName) {
        final Properties properties = new Properties();
        final URL url = CnfUtil.class.getResource("/" + fileName);
        if (url == null) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError("Could not find configuration file:" + fileName);
        }
        try (InputStream stream = url.openStream()) {
            properties.load(stream);
        } catch (final IOException e) {
            // no need to catch this error, it is handled by JOSM error
            // mechanism
            throw new ExceptionInInitializerError(e);
        }
        return properties;
    }


    /**
     * Reads an array of properties identified by the given key.
     *
     * @param properties a {@code Properties} object
     * @param key the key of the property list
     * @return an array of values
     */
    static String[] readPropertiesArray(final Properties properties, final String key) {
        final String values = properties.getProperty(key);
        return (values != null && !values.isEmpty()) ? values.split(SEPARATOR) : null;
    }


    /**
     * Reads a list of properties identifier by the given key.
     *
     * @param properties a {@code Properties} object
     * @param key the key of the property list
     * @return a list of values
     */
    static List<String> readPropertiesList(final Properties properties, final String key) {
        final String[] values = readPropertiesArray(properties, key);
        return values != null ? Arrays.asList(values) : new ArrayList<String>();
    }

    /**
     * Reads the property with the given key from the given properties.
     *
     * @param properties a {@code Properties} object
     * @param key the key of a property
     * @return the value of the property
     */
    static String readProperty(final Properties properties, final String key) {
        return properties.getProperty(key);
    }

    private CnfUtil() {}
}