package me.zjor.controller;

import com.google.inject.Provider;
import com.sun.jersey.api.view.Viewable;
import lombok.extern.slf4j.Slf4j;
import me.zjor.auth.UserId;
import me.zjor.auth.model.SocialProfile;
import me.zjor.auth.service.AuthUserService;
import me.zjor.auth.service.SocialProfileService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders JSP
 * @author: Sergey Royz
 * @since: 03.11.2013
 */
@Slf4j
@Path("/test")
public class SampleController {

    @Inject
    @UserId
    private Provider<String> userId;

    @Inject
    private AuthUserService userService;

	@Inject
	private SocialProfileService socialProfileService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response home() {

		SocialProfile profile = socialProfileService.findByAuthUser(userId.get());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", profile);

        return Response.ok(new Viewable("/home", model)).build();
    }

}
