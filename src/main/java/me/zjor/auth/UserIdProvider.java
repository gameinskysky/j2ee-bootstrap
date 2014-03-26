package me.zjor.auth;

import com.google.inject.Provider;
import me.zjor.auth.service.AuthUserService;

/**
 * @author: Sergey Royz
 * @since: 13.11.2013
 */
public class UserIdProvider implements Provider<String> {

    @Override
    public String get() {
        return AuthUserService.getUserId();
    }
}
