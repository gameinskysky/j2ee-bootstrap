package me.zjor.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zjor.auth.AuthUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: Sergey Royz
 * @since: 01.11.2013
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    /**
     * Original text entered by user
     */
    @Column(name = "source", nullable = false)
    private String source;

    /**
     * Extracted text
     * Tags are removed
     */
    @Column(name = "task", nullable = false)
    private String task;

    @Column(name = "tags", nullable = true)
    private String tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public static Task create(EntityManager em, AuthUser user, String source, String task, String tags, TaskStatus status) {
        Task t = new Task();
        t.setUser(user);
        t.setSource(source);
        t.setTask(task);
        t.setTags(tags);
        t.setStatus(status);
        t.setCreationDate(new Date());
        em.persist(t);
        return t;
    }


}
