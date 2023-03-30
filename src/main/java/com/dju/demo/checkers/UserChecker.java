package com.dju.demo.checkers;

import com.dju.demo.helpers.EmailHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UserChecker {
    private final JSONArray _allObjects;
    public UserChecker(final JSONArray all) {
        this._allObjects = all;
    }

    public boolean checkEmailValid(final String email) {
        return EmailHelper.crunchifyEmailValidator(email);
    }

    private boolean checkKeyUnused(final String key, final String value) {
        return !this._allObjects.stream().filter(o -> {
            final String target = (String)((JSONObject)o).get(key);
            return target != null && target.equals(value);
        }).findFirst().isPresent();
    }

    public boolean checkEmailUnused(final String email) {
        return this.checkKeyUnused("email", email);
    }

    public boolean checkUsernameUnused(final String username) {
        return this.checkKeyUnused("username", username);
    }
}
