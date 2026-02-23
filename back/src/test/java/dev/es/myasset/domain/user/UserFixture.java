package dev.es.myasset.domain.user;

import java.time.LocalDateTime;

public class UserFixture {

    public static User createUser() {
        return User.register(LocalDateTime.of(2026,1,1,6,0,0));
    }

    public static User createUserWithChange(String userKey, UserStatus status, UserRole role) {

        LocalDateTime time = LocalDateTime.of(2026, 01, 01, 00, 00, 00);

        return User.registerForTest(
                userKey, status, role, time, time
        );
    }
}
