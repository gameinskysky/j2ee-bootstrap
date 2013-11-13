package me.zjor.auth;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
@Data
@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_access_date")
    private Date lastAccessDate;

}
