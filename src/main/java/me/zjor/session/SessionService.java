package me.zjor.session;

import me.zjor.session.expiration.SessionExpirationPolicyChain;

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

	@Inject
	private SessionExpirationPolicyChain expirationPolicyChain;

    public SessionService() {
        Session.setService(this);
    }

    /**
     * Creates new session, persists it into database and sets as current in ThreadLocal.
     * @return
     */
    public Session create() {
        Session session = new Session();
        manager.persist(session);
        setCurrent(session);
        return session;
    }

    /**
     * Loads session and invalidates if expired.
     * If session is valid extends expiration date and sets in ThreadLocal.
     * @param sessionId
     * @return
     */
    public Session load(String sessionId) {
        Session session = manager.loadSession(sessionId);
        if (session == null) {
            return null;
        }

		session = expirationPolicyChain.process(session);
		if (session != null) {
			setCurrent(session);
		}

        return session;
    }

    public void remove(String sessionId) {
        manager.remove(sessionId);
    }

    public void update(Session session) {
        session.setExpirationDate(new Date(new Date().getTime() + Session.DEFAULT_EXTENSION_PERIOD_MILLIS));
        manager.persist(session);
    }

    public void setCurrent(Session session) {
        holder.set(session);
    }

    public Session getCurrent() {
        return holder.get();
    }

}
