package me.zjor.session;

import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Sergey Royz
 * @since: 04.11.2013
 */
@Data
public class Session {

    public static final long DEFAULT_EXPIRATION_PERIOD_MILLIS = 10L * 24 * 3600 * 1000;

    @Setter
    private static SessionService service;

    private String sessionId;

    private Map<String, String> storage;

    private Date expirationDate;

    public Session() {
        sessionId = UUID.randomUUID().toString();
        storage = new LinkedHashMap<String, String>();
        expirationDate = new Date(new Date().getTime() + DEFAULT_EXPIRATION_PERIOD_MILLIS);
    }

    protected Session(String sessionId, Map<String, String> storage, Date expirationDate) {
        this.sessionId = sessionId;
        this.storage = storage;
        this.expirationDate = expirationDate;
    }

    public static Session create() {
        if (service == null) {
            throw new RuntimeException("Session service was not initialized");
        }

        return service.create();
    }

    public static Session init(String id) {
        if (service == null) {
            throw new RuntimeException("Session service was not initialized");
        }
        return service.load(id);
    }

    public static String get(String key) {
        if (service == null) {
            throw new RuntimeException("Session service was not initialized");
        }

        Session current = service.getCurrent();
        if (current == null) {
            throw new RuntimeException("Session doesn't exist");
        }
        return current.getStorage().get(key);
    }

    public static void put(String key, String value) {
        if (service == null) {
            throw new RuntimeException("Session service was not initialized");
        }

        Session current = service.getCurrent();
        if (current == null) {
            throw new RuntimeException("Session doesn't exist");
        }

        current.getStorage().put(key, value);
        service.update(current);
    }


}
