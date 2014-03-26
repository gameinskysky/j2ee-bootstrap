package me.zjor.auth.social;

import com.google.inject.Inject;
import me.zjor.auth.AuthFilter;
import me.zjor.auth.AuthProvider;
import me.zjor.auth.model.AuthUser;
import me.zjor.auth.service.SocialProfileService;
import me.zjor.session.Session;
import me.zjor.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * Date: 24.03.2014
 */
public class FacebookAuthProvider implements AuthProvider <SocialProfileDTO>{

	private static final Pattern PUBLIC_URI_PATTERN = Pattern.compile("(/landing/?)|(/socialauth/?.*)");

	@Inject
	private FacebookAppProperties appProperties;

	@Inject
	private SocialProfileService socialProfileService;

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
	public boolean authenticate(SocialProfileDTO user) {
		AuthUser authUser;

		if (!socialProfileService.isRegistered(user)) {
			authUser = socialProfileService.registerUser(user).getUser();
		} else {
			authUser = socialProfileService.authenticate(user);
		}

		if (authUser != null) {
			Session.put(AuthFilter.SESSION_KEY_AUTH_USER_ID, authUser.getId());
			return true;
		} else {
			return false;
		}
	}

	protected static String getRedirectURI(HttpServletRequest request) {
		return HttpUtils.getBaseURL(request) + "/socialauth/fb";
	}

}
