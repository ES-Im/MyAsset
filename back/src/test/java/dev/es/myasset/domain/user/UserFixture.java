package dev.es.myasset.domain.user;

import java.time.Instant;

public class UserFixture {

    public static User createUser() {
        return User.register(Instant.parse("2026-01-01T00:00:00+09:00"));
    }

    public static User createUserWithChange(String userKey, UserStatus status, UserRole role) {

        Instant time = Instant.parse("2026-01-01T09:00:00+09:00");

        return User.registerForTest(
                userKey, status, role, time, time
        );
    }
}
