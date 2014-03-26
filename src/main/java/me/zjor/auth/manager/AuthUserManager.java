package me.zjor.auth.manager;

import com.google.inject.persist.Transactional;
import me.zjor.auth.model.AuthUser;
import me.zjor.manager.AbstractManager;
import me.zjor.util.JpaQueryUtils;

import javax.persistence.TypedQuery;
import java.util.Date;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
public class AuthUserManager extends AbstractManager {

    @Transactional
    public AuthUser create(String login, String password) {
        AuthUser u = AuthUser.create(jpa(), login, password);
        jpa().persist(u);
        return u;
    }

    public AuthUser findById(String userId) {
        return jpa().find(AuthUser.class, userId);
    }

	public AuthUser findByLogin(String login) {
		TypedQuery<AuthUser> query = jpa()
				.createQuery("SELECT u FROM AuthUser u WHERE u.login = :login", AuthUser.class)
				.setParameter("login", login);
		return JpaQueryUtils.getFirstOrNull(query);
	}

	@Transactional
    public AuthUser authenticate(String login, String password) {
        TypedQuery<AuthUser> query = jpa()
                .createQuery("SELECT u FROM AuthUser u WHERE u.login = :login AND u.password = :password", AuthUser.class)
                .setParameter("login", login)
                .setParameter("password", password);
        AuthUser user = JpaQueryUtils.getFirstOrNull(query);

		if (user != null) {
			user.touch();
		}
		return user;
    }

}
