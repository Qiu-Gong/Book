package com.qiugong.encryptcore.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author qzx 20/3/1.
 */
public class AES {

    public static final String DEFAULT_PWD = "abcdefghijklmnop";
    private static final String algorithmStr = "AES/ECB/PKCS5Padding";

    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public void init(String pwd) {
        try {
            encryptCipher = Cipher.getInstance(algorithmStr);
            decryptCipher = Cipher.getInstance(algorithmStr);
            SecretKeySpec key = new SecretKeySpec(pwd.getBytes(), "AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    byte[] encrypt(byte[] content) {
        try {
            return encryptCipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decrypt(byte[] content) {
        try {
            return decryptCipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
