package github.io.ylongo.ch08.web;

import java.net.HttpURLConnection;
import java.net.URL;

public class TestableWebClient extends WebClient1 {

    private HttpURLConnection connection;
    
    public void setHttpURLConnection(HttpURLConnection connection) {
        this.connection = connection;
    }
    
    @Override
    protected HttpURLConnection createHttpURLConnection(URL url) {
        return connection;
    }
}
