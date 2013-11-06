package me.zjor.session;

import com.google.inject.persist.Transactional;
import lombok.extern.slf4j.Slf4j;
import me.zjor.manager.AbstractManager;
import me.zjor.util.JpaQueryUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.TypedQuery;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.MessageDigest;
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

        if (model == null) {
            model = new SessionModel(
                    session.getSessionId(),
                    cipherSessionData(session),
                    session.getExpirationDate());
        } else {
            model.setSessionData(cipherSessionData(session));
        }

        jpa().persist(model);
    }

    public Session loadSession(String sessionId) {
        SessionModel model = findById(sessionId);
        if (model == null) {
            return null;
        }

        return new Session(
                sessionId,
                decipherSessionData(model.getSessionData(), sessionId),
                model.getExpirationDate());
    }

    private SessionModel findById(String id) {
        TypedQuery<SessionModel> query = jpa()
                .createQuery("SELECT s FROM SessionModel s WHERE s.sessionId = :id", SessionModel.class)
                .setParameter("id", id);
        return JpaQueryUtils.getFirstOrNull(query);
    }


    private byte[] cipherSessionData(Session session) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(session.getStorage());
            oout.close();
            byte[] data = bout.toByteArray();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(session.getSessionId()), new IvParameterSpec(new byte[16]));
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("Unable to encrypt session data: {}", e);
            return null;
        }
    }

    private Map<String, String> decipherSessionData(byte[] data, String sessionId) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getKey(sessionId), new IvParameterSpec(new byte[16]));
            data = cipher.doFinal(data);

            ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
            Map<String, String> loaded = (Map<String, String>) oin.readObject();
            log.info("loaded: {}", loaded);
            return loaded;
        } catch (Exception e) {
            log.error("Unable to decrypt session data: {}", e);
            return null;
        }
    }

    private Key getKey(String sessionId) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA1");
            md5.update(sessionId.getBytes("UTF-8"));
            return new SecretKeySpec(md5.digest(), 0, 16, "AES");
        } catch (Exception e) {
            log.error("Unable to evaluate cipher key: {}", e);
            throw new RuntimeException(e);
        }
    }

}
