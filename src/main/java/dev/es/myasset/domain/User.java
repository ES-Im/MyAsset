package dev.es.myasset.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.util.Assert.*;

@Getter
@ToString
public class User {

    private String userKey;

    private UserStatus status;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    @Nullable
    private LocalDateTime withdrawReqAt;

    private User() {}

    public static User register(String userKey, LocalDateTime current) {
        User user = new User();

        user.userKey = Objects.requireNonNull(userKey);
        user.status = UserStatus.ACTIVE;
        user.role = UserRole.USER;
        user.createdAt = current;
        user.lastLoginAt = current;

        return user;
    }

    public void requestWithdraw(LocalDateTime current) {
        state(isActive(), "Status is not active");

        this.status = Objects.requireNonNull(UserStatus.WITHDRAW_PENDING);
        this.withdrawReqAt = Objects.requireNonNull(current);
    }

    public void requestActive(LocalDateTime current) {
        state(status == UserStatus.DORMANT || status == UserStatus.WITHDRAW_PENDING,
                "Status is not Dormant or Withdraw Pending");

        this.status = Objects.requireNonNull(UserStatus.ACTIVE);
        this.lastLoginAt = Objects.requireNonNull(current);
        if(this.withdrawReqAt != null) { this.withdrawReqAt = null; }
    }

    // to-do : Domain Service scheduling
    public void markDormant(LocalDateTime current) {
        checkProperty(UserStatus.ACTIVE, this.lastLoginAt, current, 365);

        this.status = Objects.requireNonNull(UserStatus.DORMANT);
    }

    // to-do : Domain Service scheduling
    public void markWithdraw(LocalDateTime current) {
        checkProperty(UserStatus.WITHDRAW_PENDING, this.withdrawReqAt, current, 30);

        this.status = Objects.requireNonNull(UserStatus.WITHDRAWN);
    }

    //to-do : Domain Service scheduling
    public void validateDelete(LocalDateTime current) {
        checkProperty(UserStatus.WITHDRAWN, this.withdrawReqAt, current, 90);
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    // check condition for change status (time && status)
    private void checkProperty(UserStatus status
                    , LocalDateTime baseDate
                    , LocalDateTime current
                    , long dateDiff) {
        state(this.status == status, "Status condition not met");

        state(baseDate != null, "Base date is null");
        state(baseDate.isBefore(current.minusDays(dateDiff)), "Date difference condition not met");
    }
}

