package me.zjor.session;

import com.google.inject.persist.Transactional;
import lombok.extern.slf4j.Slf4j;
import me.zjor.manager.AbstractManager;
import me.zjor.util.CypherUtils;

import java.util.Map;

/**
 * @author: Sergey Royz
 * @since: 06.11.2013
 */
@Slf4j
public class SessionManager extends AbstractManager {

    @Transactional
    public void persist(Session session) {
        SessionModel model = findById(session.getSessionId());

        String encryptedData = CypherUtils.encrypt(session.getSessionId(), session.getStorage());

        if (model == null) {
            model = new SessionModel(session.getSessionId(), encryptedData, session.getExpirationDate());
        } else {
            model.setSessionData(encryptedData);
        }

        jpa().persist(model);
    }

    public Session loadSession(String sessionId) {
        SessionModel model = findById(sessionId);
        if (model == null) {
            return null;
        }
        Map<String, String> decryptedData = CypherUtils.decrypt(sessionId, model.getSessionData());
        return new Session(sessionId, decryptedData, model.getExpirationDate());
    }

    private SessionModel findById(String id) {
        return jpa().find(SessionModel.class, id);
    }





}
