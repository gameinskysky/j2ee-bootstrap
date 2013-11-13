package me.zjor.controller;

import com.google.inject.Provider;
import com.sun.jersey.api.view.Viewable;
import lombok.extern.slf4j.Slf4j;
import me.zjor.auth.AuthUserService;
import me.zjor.auth.UserId;
import me.zjor.session.Session;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Path("/jsp")
public class SampleController {

    @Inject
    @UserId
    private Provider<String> userId;

    @Inject
    private AuthUserService userService;


    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response home(@QueryParam("name") String name) {



        if (StringUtils.isEmpty(name)) {
            name = Session.get("name");
        } else {
            Session.put("name", name);
        }

        Map<String, String> model = new HashMap<String, String>();
        model.put("name", name);
        model.put("userId", userId.get());
        model.put("user", "" + userService.get());

        return Response.ok(new Viewable("/home", model)).build();
    }

}
