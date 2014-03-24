package me.zjor.session;

import lombok.extern.slf4j.Slf4j;
import me.zjor.util.CookieUtils;
import me.zjor.util.HttpUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * @since: 04.11.2013
 */
@Slf4j
public class SessionFilter implements Filter {

    public static final String SESSION_ID_COOKIE_KEY = "ssid";
    public static final Pattern LOGOUT_URI_REGEXP = Pattern.compile("/logout/?");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (LOGOUT_URI_REGEXP.matcher(request.getServletPath()).matches()) {
            CookieUtils.deleteCookie(request, response, SESSION_ID_COOKIE_KEY);
            response.sendRedirect(HttpUtils.getBaseURL(request));
        } else {

            String sessionId = CookieUtils.getCookieValue(request, SESSION_ID_COOKIE_KEY);

            if (sessionId == null || Session.init(sessionId) == null) {
                sessionId = Session.create().getSessionId();
                CookieUtils.setCookie(response, SESSION_ID_COOKIE_KEY, sessionId);
            }

            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
