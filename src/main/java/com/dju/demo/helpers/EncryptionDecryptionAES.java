package com.dju.demo.helpers;

import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.*;

public class EncryptionDecryptionAES {
    // mainl;y implemented from https://stackoverflow.com/a/34098587/3482730

    Cipher cipher;
    String transfo; // TODO rename
    private Key _secretKey;

    public EncryptionDecryptionAES() throws NoSuchPaddingException, NoSuchAlgorithmException {
        transfo = "AES";
        cipher = Cipher.getInstance(transfo);
    }

    public EncryptionDecryptionAES(final String transformartion) throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance(transformartion);
        cipher = Cipher.getInstance(transfo); // TODO check ifalways correct
    }

//    public static void main(String[] args) throws Exception {
//    }

    public String encrypt(String plainText)
            throws Exception {
        return this.encrypt(plainText, this._secretKey);
    }

    public String encrypt(String plainText, String secretKeyPath)
            throws Exception {
        final Key secretKey = this.getSecretKey(secretKeyPath);
        return this.encrypt(plainText, secretKey);
    }

    public String encrypt(String plainText, Key secretKey)
            throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public String decrypt(String encryptedText)
            throws Exception {
        return this.decrypt(encryptedText, this._secretKey);
    }

    public String decrypt(String encryptedText, String secretKeyPath)
            throws Exception {
        final Key secretKey = this.getSecretKey(secretKeyPath);
        return this.decrypt(encryptedText, secretKey);
    }

    public String decrypt(String encryptedText, Key secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public static Key getSecretKey(String secretKeyPath) throws IOException {
        /*
         create key
         If we need to generate a new key use a KeyGenerator
         If we have existing plaintext key use a SecretKeyFactory
        */
        File initialFile = new File(secretKeyPath);
        InputStream inputStream = new FileInputStream(initialFile);

        Key secretKey = null;
        ObjectInputStream oin = new ObjectInputStream(inputStream);
        try {
            secretKey = (Key) oin.readObject();
        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
        } finally {
            oin.close();
        }

        return secretKey;
    }

    public static void saveSecretKey(Key secret, String targetFile) throws IOException {
        try (OutputStream out = new FileOutputStream(targetFile)) {
            ObjectOutputStream oout = new ObjectOutputStream(out);
            try {
                oout.writeObject(secret);
            } finally {
                oout.close();
            }
        }
    }

    public void setKeyFromFile(String keyFilePath) throws IOException, NoSuchAlgorithmException {
        // check if exists
        File f = new File(keyFilePath);
        if(!f.exists() || !f.isFile() || f.isDirectory()) {
            // create it
            this._secretKey = this.createAndSaveKey(keyFilePath);
        }

        this._secretKey = EncryptionDecryptionAES.getSecretKey(keyFilePath);
    }

    private Key createAndSaveKey(String keyFilePath) throws NoSuchAlgorithmException, IOException {
        /*
         create key
         If we need to generate a new key use a KeyGenerator
         If we have existing plaintext key use a SecretKeyFactory
        */
        KeyGenerator keyGenerator = KeyGenerator.getInstance(this.transfo);
        keyGenerator.init(128); // block size is 128bits
        SecretKey secretKey = keyGenerator.generateKey();

        saveSecretKey(secretKey, keyFilePath);
        return secretKey;
    }
}