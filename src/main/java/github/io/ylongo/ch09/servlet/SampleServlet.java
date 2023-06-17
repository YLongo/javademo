package github.io.ylongo.ch09.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A SampleServlet to demonstrate the differences of unit-testing J2EE components.
 *
 * @version $Id$
 */
public class SampleServlet extends HttpServlet {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method checks to see if the authenticated property has been setup and returns its boolean value.
     *
     * @param request
     * @return
     */
    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        String authenticationAttribute = (String) session.getAttribute("authenticated");

        return Boolean.valueOf(authenticationAttribute).booleanValue();
    }
}