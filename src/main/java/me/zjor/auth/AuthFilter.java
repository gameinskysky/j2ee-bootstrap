package me.zjor.auth;

import lombok.extern.slf4j.Slf4j;
import me.zjor.session.Session;
import me.zjor.util.HttpUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
@Slf4j
public class AuthFilter implements Filter {

    public static final String SESSION_KEY_AUTH_USER_ID = "auth_user_id";
    public static final String SESSION_KEY_AUTH_NEXT = "auth_next";
    public static final String LOGIN_REDIRECT_URL = "/login/";
    public static final Pattern ALLOW_URI_REGEXP = Pattern.compile("(/login/?)|(/register/?)|(/static/.*)");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String userId = Session.get(SESSION_KEY_AUTH_USER_ID);
        if (userId == null && !allowPassThrough(request)) {
            Session.put(SESSION_KEY_AUTH_NEXT, HttpUtils.getRequestURL(request));
            response.sendRedirect(HttpUtils.getBaseURL(request) + LOGIN_REDIRECT_URL);
        } else {
            chain.doFilter(req, resp);
        }
    }

    /**
     * Checks whether we are trying go access login page
     * @param request
     * @return
     */
    private boolean allowPassThrough(HttpServletRequest request) {
        return ALLOW_URI_REGEXP.matcher(request.getServletPath()).matches();
    }

    @Override
    public void destroy() {
    }
}
