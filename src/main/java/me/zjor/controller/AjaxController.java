package me.zjor.controller;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
@Slf4j
@Path("/api/tasks")
public class AjaxController {

    private List<String> tasks = new ArrayList<String>();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTasks() {
        log.info("Requesting tasks");
        return tasks;
    }

    @POST
    @Path("/add")
    public void addTask(@FormParam("task") String task) {
        log.info("Adding task: {}", task);
        tasks.add(task);
    }

}
