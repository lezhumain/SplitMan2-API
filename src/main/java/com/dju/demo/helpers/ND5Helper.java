package com.dju.demo.helpers;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ND5Helper {
    public static String hashForSession(final String password, final String userName, final String salt)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        final String all = password + userName + salt;
        return hash(all);
    }

    public static String hash(final String all)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(all.getBytes());
        byte[] digest = md.digest();

        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }
}
