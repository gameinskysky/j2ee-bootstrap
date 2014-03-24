package me.zjor.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
public class HttpUtils {

	public static final Charset UTF_8 = Charset.forName("UTF-8");

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

	/**
	 * Parses params from string like param=val&param2=val2 into a map
	 * @param input
	 * @return
	 */
	public static Map<String, String> parseURLEncodedParams(String input) {

		Map<String, String> params = new HashMap<String, String>();
		for (NameValuePair param: URLEncodedUtils.parse(input, UTF_8)) {
			params.put(param.getName(), param.getValue());
		}

		return params;

	}

}
