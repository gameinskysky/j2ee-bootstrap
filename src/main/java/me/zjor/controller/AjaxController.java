package me.zjor.controller;

import lombok.extern.slf4j.Slf4j;
import me.zjor.manager.TaskManager;
import me.zjor.model.Task;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
@Slf4j
@Path("/api/tasks")
public class AjaxController {

    @Inject
    private TaskManager taskManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasks() {
        log.info("Requesting tasks");
        return taskManager.fetchAll();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Task addTask(@FormParam("task") String task) {
        log.info("Adding task: {}", task);
        return taskManager.add(task);
    }

    @POST
    @Path("/delete")
    public void deleteTask(@FormParam("id") String id) {
        log.info("removing task with id: {}", id);
        taskManager.remove(id);
    }

}
