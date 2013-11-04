package me.zjor.session;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Sergey Royz
 * @since: 04.11.2013
 */
@Data
public class Session {

    private static final Map<String, Session> sessions = new HashMap<String, Session>();

    private static ThreadLocal<Session> sessionHolder = new ThreadLocal<Session>();

    private String sessionId;

    private Map<String, String> storage;

    private Session() {
        sessionId = UUID.randomUUID().toString();
        storage = new LinkedHashMap<String, String>();
    }

    public static String createSession() {
        Session session = new Session();
        sessions.put(session.getSessionId(), session);
        return session.getSessionId();
    }

    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void setCurrent(Session session) {
        sessionHolder.set(session);
    }

    public static Session getCurrent() {
        return sessionHolder.get();
    }

}
