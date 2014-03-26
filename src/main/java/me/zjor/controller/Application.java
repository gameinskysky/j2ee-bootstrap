package me.zjor.controller;

import com.sun.jersey.api.view.Viewable;
import me.zjor.auth.service.AuthUserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * @author: Sergey Royz
 * @since: 14.11.2013
 */
@Path("/app")
public class Application {

    @Inject
    private AuthUserService userService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response index() {
        return Response.ok(new Viewable("/index", Collections.singletonMap("user", userService.get()))).build();
    }

}
