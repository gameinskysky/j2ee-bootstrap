package me.zjor.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
public class HttpUtils {

    /**
     * @param req
     * @return returns fill URL with query string
     */
    public static String getRequestURL(HttpServletRequest req) {

        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();
        String queryString = req.getQueryString();

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }

    /**
     * @param request
     * @return baseURL for request, i.e. {schema}://{host}[:port]/{servletName}
     */
    public static String getBaseURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        return url.replace(uri, contextPath);
    }

}
