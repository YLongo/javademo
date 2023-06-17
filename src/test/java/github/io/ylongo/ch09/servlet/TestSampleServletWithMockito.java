package github.io.ylongo.ch09.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class TestSampleServletWithMockito {
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpSession session;
    
    private SampleServlet servlet;
    
    @BeforeEach
    public void setUp() {
        servlet = new SampleServlet();
    }
    
    @Test
    public void testIsAuthenticated() {
        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("authenticated")).thenReturn("true");
        Assertions.assertTrue(servlet.isAuthenticated(request));
    }
}
