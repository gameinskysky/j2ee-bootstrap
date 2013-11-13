package me.zjor.auth;

import com.google.inject.persist.Transactional;
import me.zjor.manager.AbstractManager;
import me.zjor.util.JpaQueryUtils;

import javax.persistence.TypedQuery;
import java.util.Date;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
//TODO: update last_access_time when user logs in
public class AuthUserManager extends AbstractManager {

    @Transactional
    public AuthUser create(String login, String password) {
        AuthUser u = new AuthUser();
        u.setLogin(login);
        u.setPassword(password);
        Date now = new Date();
        u.setCreationDate(now);
        u.setLastAccessDate(now);
        jpa().persist(u);
        return u;
    }

    public AuthUser findById(String userId) {
        return jpa().find(AuthUser.class, userId);
    }

    public AuthUser authenticate(String login, String password) {
        TypedQuery<AuthUser> query = jpa()
                .createQuery("SELECT u FROM AuthUser u WHERE u.login = :login AND u.password = :password", AuthUser.class)
                .setParameter("login", login)
                .setParameter("password", password);
        return JpaQueryUtils.getFirstOrNull(query);
    }


}
