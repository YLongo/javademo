package github.io.ylongo.ch08.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.net.URL;

class TestWebClient1 {
    
    @Test
    public void testGetContentOk() throws Exception {
        MockHttpURLConnection mockConnection = new MockHttpURLConnection();
        mockConnection.setExpectedInputStream(new ByteArrayInputStream("It works".getBytes()));
        
        TestableWebClient webClient = new TestableWebClient();
        webClient.setHttpURLConnection(mockConnection);
     
        webClient.setHttpURLConnection(mockConnection);
        String result = webClient.getContent(new URL("http://localhost"));
        Assertions.assertEquals("It works", result);
    }
}
