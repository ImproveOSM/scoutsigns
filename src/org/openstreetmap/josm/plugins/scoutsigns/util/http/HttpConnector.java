package org.openstreetmap.josm.plugins.scoutsigns.util.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.openstreetmap.josm.plugins.scoutsigns.util.retry.RetryAgent;
import org.openstreetmap.josm.plugins.scoutsigns.util.retry.RetryAgentException;
import org.openstreetmap.josm.plugins.scoutsigns.util.retry.RetrySetup;


/**
 * A general HTTP connector that connects to a server and reads the content at the specified URL. This class is meant to
 * hide the details of HTTP connection setup, sending of the HTTP request, and receiving and interpretation of the HTTP
 * response.
 *
 * @author Beata
 * @version $Revision$
 */
public class HttpConnector {

    /* connects to the specified URL */
    private final class ConnectionRetryAgent extends RetryAgent<Boolean> {

        private ConnectionRetryAgent() {
            super(retrySetup);
        }

        @Override
        protected void cleanup() throws RetryAgentException {
            /* No cleanup is needed. */
        }

        @Override
        protected Boolean target() throws RetryAgentException {
            try {
                connection.connect();
            } catch (final IOException e) {
                throw new RetryAgentException(e);
            }
            return true;
        }
    }

    /*
     * reads the input stream or the error stream depending if he operation has been executed with success
     */
    private final class InputStreamRetryAgent extends RetryAgent<InputStream> {

        private final boolean successStream;

        private InputStreamRetryAgent(final boolean successStream) {
            super(retrySetup);
            this.successStream = successStream;
        }

        @Override
        protected void cleanup() throws RetryAgentException {
            /* No cleanup is needed. */
        }

        @Override
        protected InputStream target() throws RetryAgentException {
            try {
                return successStream ? connection.getInputStream() : connection.getErrorStream();
            } catch (final IOException e) {
                throw new RetryAgentException(e);
            }
        }
    }

    /* time-out settings */
    private static final int READ_TIMEOUT = 0;
    private static final int CONNECT_TIMEOUT = 10000;
    private final RetrySetup retrySetup;


    private HttpURLConnection connection;

    private boolean connected = false;

    /**
     * Builds a new {@code HttpConnector} for the given url and method using default {@code RetrySetup#DEFAULT}
     * settings.
     *
     * @param url the URL address to connect
     * @param method the HTTP method be be executed
     * @throws HttpException if an error occurred during the connection
     */
    public HttpConnector(final String url, final HttpMethod method) throws HttpException {
        this(url, method, RetrySetup.DEFAULT);
    }

    /**
     * Builds a new {@code HttpConnector} for the given url and method using the specified {@code RetrySetup} settings.
     *
     * @param url the URL address to connect
     * @param method the HTTP method be be executed
     * @param retrySetup the {@code RetrySetup} settings
     * @throws HttpException if an error occurred during the connection
     */
    public HttpConnector(final String url, final HttpMethod method, final RetrySetup retrySetup) throws HttpException {
        this.retrySetup = retrySetup;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod(method.name());
            connection.setDoOutput(true);
        } catch (final IOException e) {
            throw new HttpException(e);
        }
        connection.setRequestProperty("Connection", "close");
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
    }

    /**
     * Reads the response from the URL. If the connection has not already been established connects to the URL. The
     * response type returned depends on the received response code. If the response code is:
     * <ul>
     * <li>{@link HttpURLConnection#HTTP_OK} the method returns the content of the input stream</li>
     * <li>{@link HttpURLConnection#HTTP_NOT_FOUND} the method returns the content of the response message</li>
     * <li>otherwise the method returns the content of the error stream</li>
     * </ul>
     *
     * @return a {@code String} containing the response content
     * @throws HttpException if the input/error stream cannot be obtained or the content cannot be read
     */
    public String read() throws HttpException {
        if (!connected) {
            connect();
        }
        String response = null;
        try {
            final int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = HttpUtil.readUtf8Content(getInputStream(true));
            } else if (responseCode != HttpURLConnection.HTTP_NOT_FOUND) {
                response = HttpUtil.readUtf8Content(getInputStream(false));
            }
        } catch (final IOException e) {
            throw new HttpException(e);
        } finally {
            disconnect();
        }
        return response;
    }

    /**
     * Writes the given content to the message body using 'UTF-8' character encoding.
     *
     * @param content a map of (parameter - value) pairs representing the content to be sent to the server
     * @throws HttpException if the output stream cannot be obtained or the content cannot be sent
     */
    public void write(final Map<String, String> content) throws HttpException {
        if (!connected) {
            connect();
        }
        if (content != null) {
            final String body = HttpUtil.utf8Encode(content);
            try (BufferedWriter out =
                    new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), HttpUtil.ENCODING))) {
                out.write(body);
            } catch (final IOException ex) {
                throw new HttpException(ex);
            }
        }
    }

    private void connect() throws HttpException {
        try {
            connected = new ConnectionRetryAgent().run();
        } catch (final Exception e) {
            throw new HttpException(e);
        }
    }


    private void disconnect() throws HttpException {
        try {
            connection.disconnect();
        } catch (final Exception e) {
            throw new HttpException(e);
        }
    }


    private InputStream getInputStream(final boolean successStream) throws HttpException {
        InputStream is;
        try {
            is = new InputStreamRetryAgent(successStream).run();
        } catch (final Exception e) {
            throw new HttpException(e);
        }
        return is;
    }
}