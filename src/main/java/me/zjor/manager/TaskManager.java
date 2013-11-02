package me.zjor.manager;

import com.google.inject.persist.Transactional;
import me.zjor.model.Task;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 01.11.2013
 */
public class TaskManager {

    @Inject
    private Provider<EntityManager> emProvider;

    private EntityManager jpa() {
        return emProvider.get();
    }

    @Transactional
    public Task add(String task) {
        return Task.create(jpa(), task);
    }

    public Task findById(String taskId) {
        List<Task> result =
                jpa()
                        .createQuery("SELECT t FROM Task t WHERE t.id = :id")
                        .setParameter("id", taskId)
                        .getResultList();
        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
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
