package me.zjor.auth;

import com.sun.jersey.api.view.Viewable;
import lombok.extern.slf4j.Slf4j;
import me.zjor.session.Session;

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
@Path("/login")
public class AuthController {

    @Inject
    private UserManager userManager;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response showLoginForm() {
        return Response.ok(new Viewable("/login")).build();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response login(
            @FormParam("login") String login,
            @FormParam("password") String password
    ) {
        User user = userManager.authenticate(login, password);
        if (user == null) {
            user = userManager.create(login, password);
        }

        Session.put(AuthFilter.SESSION_KEY_AUTH_USER_ID, user.getId());
        String nextURL = Session.get(AuthFilter.SESSION_KEY_AUTH_NEXT);
        Session.remove(AuthFilter.SESSION_KEY_AUTH_NEXT);

        return Response.ok(new Viewable("/redirect",
                Collections.singletonMap("url", nextURL))).build();
    }

}
