package me.zjor.session;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author: Sergey Royz
 * @since: 07.11.2013
 */
public class SessionService {

    private static ThreadLocal<Session> holder = new ThreadLocal<Session>();

    @Inject
    private SessionManager manager;

    public SessionService() {
        Session.setService(this);
    }

    public Session create() {
        Session session = new Session();
        manager.persist(session);
        setCurrent(session);
        return session;
    }

    public Session load(String id) {
        Session session = manager.loadSession(id);
        setCurrent(session);
        update(session);
        return session;
    }

    public void update(Session session) {
        session.setExpirationDate(new Date(new Date().getTime() + Session.DEFAULT_EXPIRATION_PERIOD_MILLIS));
        manager.persist(session);
    }

    public void setCurrent(Session session) {
        holder.set(session);
    }

    public Session getCurrent() {
        return holder.get();
    }

}
