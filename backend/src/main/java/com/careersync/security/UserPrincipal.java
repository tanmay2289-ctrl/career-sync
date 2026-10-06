package com.careersync.security;

import com.careersync.model.User;
import lombok.Getter;

@Getter
public class UserPrincipal {
    private final String userId;
    private final String supabaseUid;
    private final String email;
    private final String name;

    public UserPrincipal(User user) {
        this.userId = user.getId();
        this.supabaseUid = user.getSupabaseUid();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
