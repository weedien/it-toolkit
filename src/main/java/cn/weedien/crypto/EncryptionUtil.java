package cn.weedien.crypto;

import java.nio.charset.StandardCharsets;

public class EncryptionUtil {

    public static byte[] encryptData(byte[] pText, String password) throws Exception {
        return EncryptorAesGcm.encryptWithPassword(pText, password.toCharArray());
    }

    public static byte[] decryptData(byte[] cipherText, String password) throws Exception {
        return EncryptorAesGcm.decryptWithPassword(cipherText, password.toCharArray());
    }

    public static String encryptData(String pText, String password) throws Exception {
        return CryptoUtil.hex(EncryptorAesGcm.encryptWithPassword(pText.getBytes(), password.toCharArray()));
    }

    public static String decryptData(String cText, String password) throws Exception {
        return new String(EncryptorAesGcm.decryptWithPassword(CryptoUtil.hexToBytes(cText), password.toCharArray()), StandardCharsets.UTF_8);
    }

}
