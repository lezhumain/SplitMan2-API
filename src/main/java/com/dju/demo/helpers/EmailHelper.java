package com.dju.demo.helpers;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public class EmailHelper {
    /**
     * Check email validity, see https://stackoverflow.com/a/23598542/3482730
     * @param email
     * @return
     * @throws AddressException
     */
    public static boolean crunchifyEmailValidator(String email) {
        try {
            // Create InternetAddress object and validated the supplied
            // address which is this case is an email address.
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            return false;
        }

        return true;
    }
}
