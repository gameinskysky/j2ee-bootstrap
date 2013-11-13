package me.zjor.auth;

import com.sun.jersey.api.view.Viewable;
import lombok.extern.slf4j.Slf4j;
import me.zjor.session.Session;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
@Slf4j
@Path("/")
public class AuthController {

    @Inject
    private AuthUserManager userManager;

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public Response showLoginForm() {
        return Response.ok(new Viewable("/login")).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public Response login(
            @FormParam("login") String login,
            @FormParam("password") String password
    ) {
        AuthUser user = userManager.authenticate(login, password);
        if (user == null) {
            return Response.ok(new Viewable("/login", Collections.singletonMap("failed", Boolean.TRUE))).build();
        }

        Session.put(AuthFilter.SESSION_KEY_AUTH_USER_ID, user.getId());
        String nextURL = Session.get(AuthFilter.SESSION_KEY_AUTH_NEXT);
        Session.remove(AuthFilter.SESSION_KEY_AUTH_NEXT);

        return Response.ok(new Viewable("/redirect",
                Collections.singletonMap("url", nextURL))).build();
    }

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public Response showRegistrationForm() {
        return Response.ok(new Viewable("/register")).build();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public Response register(
            @FormParam("login") String login,
            @FormParam("password") String password
    ) {
        boolean failed = false;
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            failed = true;
        }

        failed = (failed) ? failed : (userManager.findById(login) != null);
        if (failed) {
            return Response.ok(new Viewable("/register", Collections.singletonMap("failed", Boolean.TRUE))).build();
        }
        AuthUser user = userManager.create(login, password);

        Session.put(AuthFilter.SESSION_KEY_AUTH_USER_ID, user.getId());
        String nextURL = Session.get(AuthFilter.SESSION_KEY_AUTH_NEXT);
        Session.remove(AuthFilter.SESSION_KEY_AUTH_NEXT);

        return Response.ok(new Viewable("/redirect",
                Collections.singletonMap("url", nextURL))).build();
    }


}
