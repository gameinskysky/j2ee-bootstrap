package me.zjor.session;

import lombok.Data;

import javax.inject.Inject;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Sergey Royz
 * @since: 04.11.2013
 */
//TODO: rework this class
@Data
public class Session {

    public static final long DEFAULT_EXPIRATION_PERIOD_SECONDS = 10L * 24 * 3600;

    private static ThreadLocal<Session> sessionHolder = new ThreadLocal<Session>();

    @Inject
    private SessionManager manager;

    private String sessionId;

    private Map<String, String> storage;

    private Date expirationDate;

    public Session() {
        sessionId = UUID.randomUUID().toString();
        storage = new LinkedHashMap<String, String>();
        expirationDate = new Date(new Date().getTime() + DEFAULT_EXPIRATION_PERIOD_SECONDS * 1000);
    }

    protected Session(String sessionId, Map<String, String> storage, Date expirationDate) {
        this.sessionId = sessionId;
        this.storage = storage;
        this.expirationDate = expirationDate;
    }

    public String createSession() {
        Session session = new Session();
        manager.persist(session);
        return session.getSessionId();
    }

    public Session getSession(String sessionId) {
        return manager.loadSession(sessionId);
    }

    public static void setCurrent(Session session) {
        sessionHolder.set(session);
    }

    public static Session getCurrent() {
        return sessionHolder.get();
    }

}
