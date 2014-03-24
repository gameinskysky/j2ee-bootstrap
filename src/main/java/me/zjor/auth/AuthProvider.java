package me.zjor.auth;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: Sergey Royz
 * Date: 24.03.2014
 */
public interface AuthProvider {

	public String getLoginURL(HttpServletRequest request);

	public boolean isPublic(String uri);

	public void authenticate(Map<String, Object> userData);

}
