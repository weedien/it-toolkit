package cn.weedien.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * AES-GCM inputs - 12 bytes IV, need the same IV and secret keys for encryption and decryption.
 * <p>
 * The output consist of iv, encrypted content, and auth tag in the following format:
 * output = byte[] {i i i c c c c c c ...}
 * <p>
 * i = IV bytes
 * c = content bytes (encrypted content, auth tag)
 */
public class EncryptorAesGcm {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int AES_KEY_BIT = 256;
    private static final int SALT_LENGTH_BYTE = 16;

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    // AES-GCM needs GCMParameterSpec
    public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] iv) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return cipher.doFinal(pText);

    }

    public static byte[] encryptWithPassword(byte[] pText, char[] password) throws Exception {

        byte[] salt = CryptoUtil.getRandomNonce(SALT_LENGTH_BYTE);
        SecretKey secret = CryptoUtil.getAESKeyFromPassword(password, salt);

        byte[] iv = CryptoUtil.getRandomNonce(IV_LENGTH_BYTE);
        byte[] cipherText = encrypt(pText, secret, iv);

        Arrays.fill(password, Character.MIN_VALUE); // original password should be wiped
        Arrays.fill(secret.getEncoded(), (byte) 0); // derived key should be wiped

        return ByteBuffer.allocate(salt.length + iv.length + cipherText.length)
                .put(salt)
                .put(iv)
                .put(cipherText)
                .array();

    }

    public static byte[] decrypt(byte[] cText, SecretKey secret, byte[] iv) throws Exception {

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return cipher.doFinal(cText);

    }

    public static byte[] decryptWithPassword(byte[] cText, char[] password) throws Exception {

        ByteBuffer bb = ByteBuffer.wrap(cText);

        byte[] salt = new byte[SALT_LENGTH_BYTE];
        bb.get(salt);

        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        SecretKey secret = CryptoUtil.getAESKeyFromPassword(password, salt);

        byte[] plainText = decrypt(cipherText, secret, iv);

        Arrays.fill(password, Character.MIN_VALUE); // original password should be wiped
        Arrays.fill(secret.getEncoded(), (byte) 0); // derived key should be wiped

        return plainText;

    }

    public static void main(String[] args) throws Exception {

        // String OUTPUT_FORMAT = "%-30s:%s";
        //
        // String pText = "Hello World AES-GCM, Welcome to Cryptography!";
        //
        // // encrypt and decrypt need the same key.
        // // get AES 256 bits (32 bytes) key
        // SecretKey secretKey = CryptoUtils.getAESKey(AES_KEY_BIT);
        //
        // // encrypt and decrypt need the same IV.
        // // AES-GCM needs IV 96-bit (12 bytes)
        // byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);
        //
        // byte[] encryptedText = EncryptorAesGcm.encryptWithPrefixIV(pText.getBytes(UTF_8), secretKey, iv);
        //
        // System.out.println("\n------ AES GCM Encryption ------");
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Input (plain text)", pText);
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Key (hex)", CryptoUtils.hex(secretKey.getEncoded()));
        // System.out.printf((OUTPUT_FORMAT) + "%n", "IV  (hex)", CryptoUtils.hex(iv));
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Encrypted (hex) ", CryptoUtils.hex(encryptedText));
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Encrypted (hex) (block = 16)", CryptoUtils.hexWithBlockSize(encryptedText, 16));
        //
        // System.out.println("\n------ AES GCM Decryption ------");
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Input (hex)", CryptoUtils.hex(encryptedText));
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Input (hex) (block = 16)", CryptoUtils.hexWithBlockSize(encryptedText, 16));
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Key (hex)", CryptoUtils.hex(secretKey.getEncoded()));
        //
        // String decryptedText = EncryptorAesGcm.decryptWithPrefixIV(encryptedText, secretKey);
        //
        // System.out.printf((OUTPUT_FORMAT) + "%n", "Decrypted (plain text)", decryptedText);

    }

}