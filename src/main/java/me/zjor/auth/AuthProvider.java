package me.zjor.auth;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Sergey Royz
 * Date: 24.03.2014
 */
public interface AuthProvider<T> {

	public String getLoginURL(HttpServletRequest request);

	public boolean isPublic(String uri);

	public boolean authenticate(T user);

}
