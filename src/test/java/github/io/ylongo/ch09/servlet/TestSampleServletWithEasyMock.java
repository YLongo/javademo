package github.io.ylongo.ch09.servlet;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TestSampleServletWithEasyMock {
    
    private SampleServlet servlet;
    
    private HttpServletRequest mockHttpServletRequest;
    
    private HttpSession mockHttpSession;
    
    @BeforeEach
    public void setUp() {
        servlet = new SampleServlet();
        mockHttpServletRequest = EasyMock.createStrictMock(HttpServletRequest.class);
        mockHttpSession = EasyMock.createStrictMock(HttpSession.class);
    }
    
    @Test
    public void testIsAuthenticated() {
        EasyMock.expect(mockHttpServletRequest.getSession(false)).andReturn(mockHttpSession);
        EasyMock.expect(mockHttpSession.getAttribute("authenticated")).andReturn("true");
        EasyMock.replay(mockHttpServletRequest);
        EasyMock.replay(mockHttpSession);
        servlet.isAuthenticated(mockHttpServletRequest);
        EasyMock.verify(mockHttpServletRequest);
        EasyMock.verify(mockHttpSession);
    }
}
