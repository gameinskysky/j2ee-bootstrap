package me.zjor.auth;

import me.zjor.manager.AbstractManager;
import me.zjor.util.JpaQueryUtils;

import javax.persistence.TypedQuery;
import java.util.Date;

/**
 * @author: Sergey Royz
 * @since: 09.11.2013
 */
public class UserManager extends AbstractManager {

    public User create(String login, String password) {
        User u = new User();
        u.setLogin(login);
        u.setPassword(password);
        Date now = new Date();
        u.setCreationDate(now);
        u.setLastAccessDate(now);
        jpa().persist(u);
        return u;
    }

    public User findById(String userId) {
        return jpa().find(User.class, userId);
    }

    public User authenticate(String login, String password) {
        TypedQuery<User> query = jpa()
                .createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password", User.class)
                .setParameter("login", login)
                .setParameter("password", password);
        return JpaQueryUtils.getFirstOrNull(query);
    }


}
