package me.zjor.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Sergey Royz
 * @since: 04.11.2013
 */
public class CookieUtils {

    private static final String COOKIE_ROOT_PATH = "/";

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (name == null || name.length() == 0 || cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie c: cookies) {
            if (name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    protected static void setCookie(HttpServletResponse response, String key, String value, int expiry, String path, String domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        if (path != null)
            cookie.setPath(path);
        if (domain != null)
            cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static void setCookie(HttpServletResponse response, String key, String value) {
        setCookie(response, key, value, -1, COOKIE_ROOT_PATH, null);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie cookie = getCookie(request, key);
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath(COOKIE_ROOT_PATH);
            response.addCookie(cookie);
        }
    }

}
