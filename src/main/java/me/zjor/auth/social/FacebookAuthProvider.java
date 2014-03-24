package me.zjor.auth.social;

import com.google.inject.Inject;
import me.zjor.auth.AuthProvider;
import me.zjor.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * Date: 24.03.2014
 */
public class FacebookAuthProvider implements AuthProvider{

	private static final Pattern PUBLIC_URI_PATTERN = Pattern.compile("(/landing/?)|(/socialauth/?.*)");

	@Inject
	private FacebookAppProperties appProperties;

	@Override
	public String getLoginURL(HttpServletRequest request) {
		StringBuilder urlBuilder = new StringBuilder("https://www.facebook.com/dialog/oauth?client_id=");
		urlBuilder.append(appProperties.getClientId());
		urlBuilder.append("&redirect_uri=").append(getRedirectURI(request));
		return urlBuilder.toString();
	}

	@Override
	public boolean isPublic(String uri) {
		return PUBLIC_URI_PATTERN.matcher(uri).matches();
	}

	@Override
	public void authenticate(Map<String, Object> userData) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	protected static String getRedirectURI(HttpServletRequest request) {
		return HttpUtils.getBaseURL(request) + "/socialauth/fb";
	}

}
