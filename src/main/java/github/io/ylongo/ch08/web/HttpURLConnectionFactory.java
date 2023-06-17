package github.io.ylongo.ch08.web;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP implementation of the connection factory.
 */
public class HttpURLConnectionFactory implements ConnectionFactory {
    /**
     * URL for the connection.
     */
    private URL url;

    /**
     * Constructor with the url as a parameter.
     *
     * @param url
     */
    public HttpURLConnectionFactory(URL url) {
        this.url = url;
    }

    /**
     * Read the data from the HTTP input stream.
     *
     * @return
     */
    public InputStream getData() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
        return connection.getInputStream();
    }
}