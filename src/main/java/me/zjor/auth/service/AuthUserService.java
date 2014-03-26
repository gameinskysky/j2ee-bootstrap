package me.zjor.auth.service;

import me.zjor.auth.manager.AuthUserManager;
import me.zjor.auth.model.AuthUser;

import javax.inject.Inject;

/**
 * @author: Sergey Royz
 * @since: 13.11.2013
 */
public class AuthUserService {

    private static ThreadLocal<String> userIdHolder = new ThreadLocal<String>();

    @Inject
    private AuthUserManager userManager;

    public static void setUserId(String id) {
        userIdHolder.set(id);
    }

    public static String getUserId() {
        return userIdHolder.get();
    }

    /**
     * Returns logged in user.
     * @return currently logged in user or null
     */
    public AuthUser get() {
        String userId = getUserId();
        if (userId == null) {
            return null;
        }

        return userManager.findById(userId);
    }

}
