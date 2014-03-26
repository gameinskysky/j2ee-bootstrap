package me.zjor.auth;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import me.zjor.auth.service.AuthUserService;
import me.zjor.session.Session;
import me.zjor.util.HttpUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
@Slf4j
public class AuthFilter implements Filter {

    public static final String SESSION_KEY_AUTH_USER_ID = "auth_user_id";
    public static final String SESSION_KEY_AUTH_NEXT = "auth_next";

	@Inject
	private AuthProvider authProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String userId = Session.get(SESSION_KEY_AUTH_USER_ID);
        if (userId == null && !authProvider.isPublic(request.getServletPath())) {
            Session.put(SESSION_KEY_AUTH_NEXT, HttpUtils.getRequestURL(request));
            response.sendRedirect(authProvider.getLoginURL(request));
        } else {
            AuthUserService.setUserId(userId);
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
