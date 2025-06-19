package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtils {
    private static final String ALGO = "AES";
    private static final byte[] KEY = new byte[] { 'M', 'y', 'S', 'e', 'c', 'K', 'e', 'y', '1', '2', '3', '4', '5', '6', '7', '8' };

    public static String encrypt(String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedVal = Base64.getDecoder().decode(encryptedData);
        byte[] decVal = cipher.doFinal(decodedVal);
        return new String(decVal);
    }
}
