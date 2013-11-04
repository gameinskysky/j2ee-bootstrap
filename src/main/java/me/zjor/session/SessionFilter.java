package me.zjor.session;

import me.zjor.util.CookieUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Sergey Royz
 * @since: 04.11.2013
 */
public class SessionFilter implements Filter {

    public static final String SESSION_ID_COOKIE_KEY = "ssid";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String sessionId = CookieUtils.getCookieValue(request, SESSION_ID_COOKIE_KEY);

        if (sessionId == null || Session.getSession(sessionId) == null) {
            sessionId = Session.createSession();
            CookieUtils.setCookie(response, SESSION_ID_COOKIE_KEY, sessionId);
        }

        Session.setCurrent(Session.getSession(sessionId));

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
