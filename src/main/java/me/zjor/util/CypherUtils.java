package me.zjor.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;

/**
 * @author: Sergey Royz
 * @since: 07.11.2013
 */
@Slf4j
public class CypherUtils {

    private static Key getKey(String source) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA1");
            md5.update(source.getBytes("UTF-8"));
            return new SecretKeySpec(md5.digest(), 0, 16, "AES");

        } catch (Exception e) {
            CypherUtils.log.error("Unable to evaluate key: {}", e);
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String keySource, Object data) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(data);
            oout.close();

            byte[] dataBytes = bout.toByteArray();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(keySource), new IvParameterSpec(new byte[16]));

            return Base64.encodeBase64String(cipher.doFinal(dataBytes));
        } catch (Exception e) {
            CypherUtils.log.error("Unable to encrypt data: {}", e);
            return null;
        }
    }

    public static <T> T decrypt(String keySource, String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getKey(keySource), new IvParameterSpec(new byte[16]));
            byte[] dataBytes = cipher.doFinal(Base64.decodeBase64(data));

            ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(dataBytes));
            return (T) oin.readObject();
        } catch (Exception e) {
            CypherUtils.log.error("Unable to decrypt data: {}", e);
            return null;
        }
    }

}
