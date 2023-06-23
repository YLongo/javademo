package github.io.ylongo.ch08.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class TestWebClient2 {
    
    @Test
    public void testGetContentOk() throws Exception {
        MockConnectionFactory mockConnectionFactory = new MockConnectionFactory();
        mockConnectionFactory.setData(new ByteArrayInputStream("It works".getBytes()));
        
        WebClient2 webClient = new WebClient2();
        String result = webClient.getContent(mockConnectionFactory);
        Assertions.assertEquals("It works", result);
    }
}
