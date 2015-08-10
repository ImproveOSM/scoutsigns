package org.openstreetmap.josm.plugins.scoutsigns.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.io.IOUtils;


/**
 * Utility class, contains utility methods for the HTTP communication.
 *
 * @author Beata
 * @version $Revision$
 */
public final class HttpUtil {

    public static final String ENCODING = "utf-8";

    /**
     * Encodes the given collection of strings using {@code HttpUtil#ENCODING} encoding.
     *
     * @param col a collection of {@code String}s
     * @return the encoded string
     */
    public static String utf8Encode(final Collection<String> col) {
        final StringBuilder param = new StringBuilder();
        for (final String elem : col) {
            param.append(elem).append(",");
        }
        return utf8Encode(param.substring(0, param.length() - 1));
    }


    /**
     * Encodes the given parameter using {@code HttpUtil#ENCODING} encoding.
     *
     * @param param the parameter to be encoded
     * @return the encoded parameter
     */
    public static String utf8Encode(final String param) {
        String encodedParam = null;
        try {
            encodedParam = URLEncoder.encode(param, ENCODING);
        } catch (final UnsupportedEncodingException ex) {
            /* should not appear since UTF-8 is a supported encoding */
        }
        return encodedParam;
    }

    /**
     * Reads the content of the given input stream and returns in string format.
     *
     * @param input a {@code InputStream} the stream which content will be read
     * @return a {@code String} containing the content of the input stream
     * @throws HttpException if the reading operation failed
     */
    static String readUtf8Content(final InputStream input) throws HttpException {
        String result;
        try {
            final StringWriter writer = new StringWriter();
            IOUtils.copy(input, writer, ENCODING);
            result = writer.toString();
        } catch (final IOException ex) {
            throw new HttpException(ex);
        } finally {
            IOUtils.closeQuietly(input);
        }
        return result;
    }

    /**
     * Encodes the given map of (parameter-value) pairs, using {@code HttpUtil#ENCODING} encoding. This method returns a
     * string of the following format: 'param1=val1&...paramN=valN'.
     *
     * @param params a map of ({@code String},{@code String}) pairs representing the input parameters of a method.
     * @return a {@code String} containing the list of encoded (parameter,value) pairs
     * @throws HttpException if the encoding failed
     */
    static String utf8Encode(final Map<String, String> params) {
        final StringBuilder result = new StringBuilder();
        for (final Map.Entry<String, String> param : params.entrySet()) {
            try {
                result.append(URLEncoder.encode(param.getKey(), ENCODING));
                result.append("=");
                result.append(URLEncoder.encode(param.getValue(), ENCODING));
                result.append("&");
            } catch (final UnsupportedEncodingException ex) {
                /* should not appear since UTF-8 is a supported encoding */
            }
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    private HttpUtil() {}
}