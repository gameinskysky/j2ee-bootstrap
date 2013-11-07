package me.zjor.controller;

import com.sun.jersey.api.view.Viewable;
import lombok.extern.slf4j.Slf4j;
import me.zjor.session.Session;
import me.zjor.session.SessionManager;
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
    private SessionManager sessionManager;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response home(@QueryParam("name") String name) {

        Session session = Session.getCurrent();

        if (StringUtils.isEmpty(name)) {
            log.info("name is empty, loading from session: {}", Session.getCurrent().getStorage());
            name = session.getStorage().get("name");
        } else {
            session.getStorage().put("name", name);
            sessionManager.persist(session);
        }

        Map<String, String> model = new HashMap<String, String>();
        model.put("name", name);

        return Response.ok(new Viewable("/home", model)).build();
    }

}
