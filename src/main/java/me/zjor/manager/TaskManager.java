package me.zjor.manager;

import com.google.inject.persist.Transactional;
import me.zjor.model.Task;
import me.zjor.util.JpaQueryUtils;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 01.11.2013
 */
public class TaskManager extends AbstractManager {

    @Transactional
    public Task add(String task) {
        return Task.create(jpa(), task);
    }

    public Task findById(String taskId) {
        return jpa().find(Task.class, taskId);
    }

    @Transactional
    public void remove(String id) {
        Task task = findById(id);
        if (task != null) {
            jpa().remove(task);
        }
    }

    public List<Task> fetchAll() {
        return jpa().createQuery("SELECT t FROM Task t ORDER BY t.creationDate ASC", Task.class).getResultList();
    }


}
