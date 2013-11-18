package me.zjor.app.manager;

import com.google.inject.persist.Transactional;
import lombok.extern.slf4j.Slf4j;
import me.zjor.app.service.TaskService;
import me.zjor.auth.AuthUser;
import me.zjor.app.model.Task;
import me.zjor.manager.AbstractManager;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * @author: Sergey Royz
 * @since: 01.11.2013
 */
@Slf4j
public class TaskManager extends AbstractManager {

    @Transactional
    public Task add(AuthUser user, String source) {
        return Task.create(
                jpa(),
                user,
                source,
                TaskService.removeTags(source),
                StringUtils.join(TaskService.extractTags(source), ','));
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

    public List<Task> fetchAll(String userId) {
        return jpa()
                .createQuery("SELECT t FROM Task t WHERE t.user.id = :id ORDER BY t.creationDate ASC", Task.class)
                .setParameter("id", userId)
                .getResultList();
    }


}
