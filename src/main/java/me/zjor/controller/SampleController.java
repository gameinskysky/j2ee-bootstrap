package me.zjor.controller;

import com.sun.jersey.api.view.Viewable;
import me.zjor.session.Session;
import org.apache.commons.lang3.StringUtils;

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
@Path("/jsp")
public class SampleController {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response home(@QueryParam("name") String name) {

        if (StringUtils.isEmpty(name)) {
            name = Session.getCurrent().getStorage().get("name");
        } else {
            Session.getCurrent().getStorage().put("name", name);
        }

        Map<String, String> model = new HashMap<String, String>();
        model.put("name", name);

        return Response.ok(new Viewable("/home", model)).build();
    }

}
