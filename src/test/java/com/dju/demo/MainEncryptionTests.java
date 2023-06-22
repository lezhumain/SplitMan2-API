package com.dju.demo;

import com.dju.demo.helpers.EncryptionDecryptionAES;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;


@SpringBootTest
class MainEncryptionTests {
    public MainEncryptionTests() {
    }

    @Test
    void checkEncrypt () throws Exception {
        /*
         create key
         If we need to generate a new key use a KeyGenerator
         If we have existing plaintext key use a SecretKeyFactory
        */
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // block size is 128bits
        SecretKey secretKey = keyGenerator.generateKey();

        //        saveSecretKey(secretKey, "my_secret_key");


        /*
          Cipher Info
          Algorithm : for the encryption of electronic data
          mode of operation : to avoid repeated blocks encrypt to the same values.
          padding: ensuring messages are the proper length necessary for certain ciphers
          mode/padding are not used with stream ciphers.
         */
        //        cipher = Cipher.getInstance("AES"); //SunJCE provider AES algorithm, mode(optional) and padding schema(optional)

        String plainText = "AES Symmetric Encryption Decryption";
        checkEncryptBase(plainText, secretKey);
    }

    @Test
    void checkEncryptFromFile () throws Exception {
        Key secretKey = EncryptionDecryptionAES.getSecretKey("my_secret_key");
        String plainText = "AES Symmetric Encryption Decryption";
        checkEncryptBase(plainText, secretKey);
    }

    private void checkEncryptBase(String plainText, Key secretKey) throws Exception {
        System.out.println("Plain Text Before Encryption: " + plainText);

        final EncryptionDecryptionAES helper = new EncryptionDecryptionAES();

        String encryptedText = helper.encrypt(plainText, secretKey);
        System.out.println("Encrypted Text After Encryption: " + encryptedText);

        String decryptedText = helper.decrypt(encryptedText, secretKey);
        System.out.println("Decrypted Text After Decryption: " + decryptedText);

        Assert.assertEquals(decryptedText, plainText);
    }
}
