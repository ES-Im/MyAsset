package dev.es.myasset.domain.common;

import dev.es.myasset.domain.user.User;

import java.time.LocalDateTime;

public class UserFixture {

    public static User createUser() {
        return User.register(LocalDateTime.of(2026,1,1,6,0,0));
    }

}
