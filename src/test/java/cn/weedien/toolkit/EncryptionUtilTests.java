package cn.weedien.toolkit;

import cn.weedien.toolkit.crypto.EncryptionUtil;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionUtilTests {

    private static final String PASSWORD = "OqDj1AN6jrt3ALeasQxO5giGwAOP7tzS";

    @Test
    void testEncryptAndDecryptDataWithBytes() throws Exception {
        byte[] plainText = "Hello, World!".getBytes();

        byte[] cipherText = EncryptionUtil.encryptData(plainText, PASSWORD);
        byte[] decryptedText = EncryptionUtil.decryptData(cipherText, PASSWORD);

        assertArrayEquals(plainText, decryptedText);
    }

    @Test
    void testEncryptAndDecryptDataWithString() throws Exception {
        String plainText = "Hello, World!";

        String cipherText = EncryptionUtil.encryptData(plainText, PASSWORD);
        String decryptedText = EncryptionUtil.decryptData(cipherText, PASSWORD);

        assertEquals(plainText, decryptedText);
    }

    @Test
    void testEncryptAndDecryptFile() throws Exception {
        byte[] fileContent = Files.readAllBytes(Paths.get("src/test/resources/testFile.txt"));

        byte[] cipherText = EncryptionUtil.encryptData(fileContent, PASSWORD);
        byte[] decryptedText = EncryptionUtil.decryptData(cipherText, PASSWORD);

        assertArrayEquals(fileContent, decryptedText);
    }
}