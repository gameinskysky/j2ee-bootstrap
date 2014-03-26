package me.zjor.auth.social;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;
import com.sun.jersey.api.view.Viewable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.zjor.auth.AuthFilter;
import me.zjor.auth.model.SocialProfile;
import me.zjor.session.Session;
import me.zjor.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.Collections;

/**
 * @author: Sergey Royz
 * Date: 23.03.2014
 */
@Slf4j
@Path("/socialauth/fb")
public class FacebookAuthController {

	@Inject
	private FacebookAuthProvider authProvider;

	@Context
	private HttpServletRequest httpServletRequest;

	@Inject
	private FacebookAppProperties appProperties;

	@Inject
	private HttpClient httpClient;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response handleRedirect(@QueryParam("code") String code) {
		if (StringUtils.isEmpty(code)) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		try {
			String accessToken = exchangeCodeForToken(code);
			log.info("Obtained access token: {}", accessToken);

			UserInfoDTO userInfo = getUserInfo(accessToken);
			log.info("Received user info: {}", userInfo);

			if (authProvider.authenticate(userInfo.toSocialProfileDTO())) {
				String nextURL = Session.get(AuthFilter.SESSION_KEY_AUTH_NEXT);
				Session.remove(AuthFilter.SESSION_KEY_AUTH_NEXT);

				return Response.ok(new Viewable("/redirect",
						Collections.singletonMap("url", nextURL))).build();
			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed").build();
			}
		} catch (Exception e) {
			log.error("Error while exchanging code for access token", e);
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		}
	}

	/**
	 * Exchanges code for access token
	 * @param code code obtained after user allowed access to the app
	 * @return
	 * @throws Exception
	 */
	private String exchangeCodeForToken(String code) throws Exception {
		StringBuilder requestBuilder =
				new StringBuilder("https://graph.facebook.com/oauth/access_token?client_id=")
				.append(appProperties.getClientId())
				.append("&redirect_uri=").append(FacebookAuthProvider.getRedirectURI(httpServletRequest))
				.append("&client_secret=").append(appProperties.getClientSecret())
				.append("&code=").append(code);

		HttpGet exchangeRequest = new HttpGet(requestBuilder.toString());
		HttpResponse httpResponse = httpClient.execute(exchangeRequest);

		if (HttpURLConnection.HTTP_OK != httpResponse.getStatusLine().getStatusCode()) {
			log.warn("Failed to exchange code for access token: {}", httpResponse.getStatusLine().getReasonPhrase());
			return null;
		}

		String response = EntityUtils.toString(httpResponse.getEntity());
		String accessToken = HttpUtils.parseURLEncodedParams(response).get("access_token");
		return accessToken;
	}

	private UserInfoDTO getUserInfo(String accessToken) throws Exception {

		StringBuilder requestBuilder = new StringBuilder("https://graph.facebook.com/me?fields=id,name,first_name,last_name,username,picture&access_token=");
		requestBuilder.append(accessToken);
		HttpGet userInfoRequest = new HttpGet(requestBuilder.toString());
		HttpResponse httpResponse = httpClient.execute(userInfoRequest);

		if (HttpURLConnection.HTTP_OK != httpResponse.getStatusLine().getStatusCode()) {
			log.warn("Failed to get user info: {}", httpResponse.getStatusLine().getReasonPhrase());
			return null;
		}

		String response = EntityUtils.toString(httpResponse.getEntity());
		UserInfoDTO userInfo = new GsonBuilder().create().fromJson(response, UserInfoDTO.class);
		return userInfo;
	}

	@Data
	public class UserInfoDTO {

		@SerializedName("id")
		private String id;

		@SerializedName("first_name")
		private String firstName;

		@SerializedName("last_name")
		private String lastName;

		@SerializedName("name")
		private String name;

		@SerializedName("username")
		private String username;

		@SerializedName("picture")
		private PictureDTO picture;

		public PictureDTO getPicture() {
			if (picture == null) {
				picture = new PictureDTO();
			}
			return picture;
		}

		public SocialProfileDTO toSocialProfileDTO() {
			SocialProfileDTO dto = new SocialProfileDTO();
			dto.setSocialNetwork(SocialProfile.SocialNetwork.FACEBOOK);
			dto.setProfileId(id);
			dto.setFirstName(firstName);
			dto.setLastName(lastName);
			dto.setUsername(username);
			dto.setAvatarURL(getPicture().getData().getUrl());
			return dto;
		}

	}

	@Data
	public static class PictureDTO {

		@SerializedName("data")
		private DataDTO data;

		public DataDTO getData() {
			if (data == null) {
				data = new DataDTO();
			}
			return data;
		}

	}

	@Data
	public static class DataDTO {

		@SerializedName("url")
		private String url;

	}


}
