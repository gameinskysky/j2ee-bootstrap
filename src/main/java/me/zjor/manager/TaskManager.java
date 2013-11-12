package me.zjor.manager;

import com.google.inject.persist.Transactional;
import lombok.extern.slf4j.Slf4j;
import me.zjor.model.Task;
import me.zjor.util.JpaQueryUtils;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 01.11.2013
 */
@Slf4j
public class TaskManager extends AbstractManager {

    @Transactional
    public Task add(String task) {
        return Task.create(jpa(), task);
    }

    public Task findById(String taskId) {
        return jpa().find(Task.class, taskId);
    }

    public void remove(String id) {
        Task task = findById(id);
        if (task != null) {
            EntityTransaction transaction = jpa().getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            jpa().remove(task);
            jpa().getTransaction().commit();
        }
    }

    public List<Task> fetchAll() {
        return jpa().createQuery("SELECT t FROM Task t ORDER BY t.creationDate ASC", Task.class).getResultList();
    }


}
