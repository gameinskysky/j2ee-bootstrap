package me.zjor.auth.model;

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

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "last_access_date", nullable = false)
    private Date lastAccessDate;

	public static AuthUser create(EntityManager em, String login, String password) {
		AuthUser user = new AuthUser();
		user.login = login;
		user.password = password;
		Date now = new Date();
		user.creationDate = now;
		user.lastAccessDate = now;
		return user;
	}

	/**
	 * Should be used in @Transactional method
	 */
	public void touch() {
		lastAccessDate = new Date();
	}

}
