package github.io.ylongo.ch08.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The mock implementation of the HttpURLConnection.
 */
public class MockHttpURLConnection extends HttpURLConnection {
    /**
     * The input stream for the connection.
     */
    private InputStream stream;

    /**
     * Constructor.
     */
    public MockHttpURLConnection() {
        super(null);
    }

    /**
     * Constructor that accepts the URL of the connection as a parameter.
     *
     * @param url
     */
    protected MockHttpURLConnection(URL url) {
        super(url);
    }

    /**
     * Setup the input stream expectation.
     *
     * @param stream
     */
    public void setExpectedInputStream(InputStream stream) {
        this.stream = stream;
    }

    /**
     * Return the input stream
     *
     * @return
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {
        return this.stream;
    }

    /**
     * Disconnect the connection.
     */
    public void disconnect() {
    }

    /**
     * Connect the connection.
     *
     * @throws IOException
     */
    public void connect() throws IOException {
    }

    /**
     * Are we using a proxy?
     *
     * @return
     */
    public boolean usingProxy() {
        return false;
    }
}