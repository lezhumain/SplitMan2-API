package com.dju.demo.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class InviteLinkHelper {
    private static String keyFilePath = "my_secret_key";
    public static String generateLink(final InviteLinkData data) throws Exception {
        final String token = String.format("%s_%s", data.getUserID(), data.getTravelID());

        // get key
        final EncryptionDecryptionAES encryptor = new EncryptionDecryptionAES();

        // load key
        encryptor.setKeyFromFile(keyFilePath);

        final String encrypted = encryptor.encrypt(token);
        return urlEncode(encrypted);
    }

    public static InviteLinkData parseLink(final String myLink) throws Exception {
        final String decoded = InviteLinkHelper.urlDecode(myLink);

        // get key
        final EncryptionDecryptionAES encryptor = new EncryptionDecryptionAES();

        // load key
        encryptor.setKeyFromFile(keyFilePath);

        final String decrypted = encryptor.decrypt(decoded);

        final String[] bits = decrypted.split("_");
        if(bits.length != 2) {
            return null;
        }

        return new InviteLinkData(Integer.parseInt(bits[0]), Integer.parseInt(bits[1]));
    }

    private static String urlEncode(final String value) {
        // Method to encode a string value using `UTF-8` encoding scheme
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    private static String urlDecode(String myLink) {
        try {
            return URLDecoder.decode(myLink, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
