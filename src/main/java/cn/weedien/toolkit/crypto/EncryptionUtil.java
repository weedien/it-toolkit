package cn.weedien.toolkit.crypto;

import cn.weedien.toolkit.encode.EncodingUtil;

import java.nio.charset.StandardCharsets;

public class EncryptionUtil {

    public static byte[] encryptData(byte[] pText, String password) throws Exception {
        return EncryptorAesGcm.encryptWithPassword(pText, password.toCharArray());
    }

    public static byte[] decryptData(byte[] cipherText, String password) throws Exception {
        return EncryptorAesGcm.decryptWithPassword(cipherText, password.toCharArray());
    }

    public static String encryptData(String pText, String password) throws Exception {
        return EncodingUtil.hexEncode(EncryptorAesGcm.encryptWithPassword(pText.getBytes(), password.toCharArray()));
    }

    public static String decryptData(String cText, String password) throws Exception {
        return new String(EncryptorAesGcm.decryptWithPassword(EncodingUtil.hexDecode(cText), password.toCharArray()), StandardCharsets.UTF_8);
    }

}
