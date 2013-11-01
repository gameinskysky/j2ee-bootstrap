package me.zjor.manager;

import me.zjor.guice.TestRunner;
import me.zjor.model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.Assert.assertEquals;

/**
 * @author: Sergey Royz
 * @since: 01.11.2013
 */
@RunWith(TestRunner.class)
public class TaskManagerTest {

    @Inject
    private EntityManager em;

    @Inject
    private TaskManager taskManager;

    @Before
    public void setUp() throws Exception {
        em.getTransaction().begin();
    }

    @After
    public void tearDown() throws Exception {
        if (em.isOpen()) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @Test
    public void testCreation() {
        Task t = taskManager.add("todo");
        em.flush();

        TypedQuery<Task> query = em.createQuery(
                "SELECT t FROM Task t WHERE t.id = :id", Task.class).setParameter("id", t.getId());
        assertEquals(t.getId(), query.getSingleResult().getId());
    }

    @Test
    public void testFetch() {
        taskManager.add("todo");
        em.flush();

        assertEquals(1, taskManager.fetchAll().size());
    }

}
