package me.zjor.app.controller;

import lombok.extern.slf4j.Slf4j;
import me.zjor.app.dto.TaskDTO;
import me.zjor.app.manager.TaskManager;
import me.zjor.auth.AuthUserService;

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

    @Inject
    private AuthUserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskDTO> getTasks() {
        return TaskDTO.fromCollection(taskManager.fetchAll(userService.get().getId()));
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public TaskDTO addTask(@FormParam("task") String task) {
        AjaxController.log.info("Adding task: {}", task);
        return TaskDTO.fromModel(taskManager.add(userService.get(), task));
    }

    @POST
    @Path("/delete")
    public void deleteTask(@FormParam("id") String id) {
        AjaxController.log.info("removing task with id: {}", id);
        taskManager.remove(id);
    }

}
