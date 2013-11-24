package me.zjor.app.controller;

import lombok.extern.slf4j.Slf4j;
import me.zjor.app.dto.TaskDTO;
import me.zjor.app.manager.TaskManager;
import me.zjor.app.model.TaskStatus;
import me.zjor.app.service.TaskService;
import me.zjor.auth.AuthUserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

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
    private TaskService taskService;

    @Inject
    private AuthUserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getTasks() {
        Map<String, Object> tasks = new HashMap<String, Object>();
        tasks.put("active", TaskDTO.fromCollection(taskManager.find(userService.get().getId(), TaskStatus.ACTIVE)));
        tasks.put("done", TaskDTO.fromCollection(taskManager.find(userService.get().getId(), TaskStatus.DONE)));
        return tasks;
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
        taskService.setStatus(id, TaskStatus.DELETED);
    }

    @POST
    @Path("/complete")
    public void completeTask(@FormParam("id") String id) {
        taskService.setStatus(id, TaskStatus.DONE);
    }


}
