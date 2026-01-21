package dev.es.myasset.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.TemporalType.TIMESTAMP;
import static java.util.Objects.*;
import static org.springframework.util.Assert.*;

@Entity
@Table(name = "users")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name="user_key")
    private String userKey;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Temporal(value = TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(value = TIMESTAMP)
    private LocalDateTime lastLoginAt;

    @Temporal(value = TIMESTAMP)
    private LocalDateTime withdrawReqAt;

    public static User register(LocalDateTime current) {
        User user = new User();

        user.userKey = UUID.randomUUID().toString();
        user.status = UserStatus.ACTIVE;
        user.role = UserRole.USER;
        user.createdAt = requireNonNull(current);
        user.lastLoginAt = requireNonNull(current);

        return user;
    }


    public void requestWithdraw(LocalDateTime current) {
        state(isActive(), "Status is not active");

        this.status = UserStatus.WITHDRAW_PENDING;
        this.withdrawReqAt = requireNonNull(current);
    }

    public void requestActive(LocalDateTime current) {
        state(status == UserStatus.DORMANT || status == UserStatus.WITHDRAW_PENDING,
                "Status is not Dormant or Withdraw Pending");

        this.status = UserStatus.ACTIVE;
        this.lastLoginAt = requireNonNull(current);
        if(this.withdrawReqAt != null) { this.withdrawReqAt = null; }
    }

    // to-do : Domain Service scheduling
    public void markDormant(LocalDateTime current) {
        checkProperty(UserStatus.ACTIVE, this.lastLoginAt, requireNonNull(current), 365);

        this.status = UserStatus.DORMANT;
    }

    // to-do : Domain Service scheduling
    public void markWithdraw(LocalDateTime current) {
        checkProperty(UserStatus.WITHDRAW_PENDING, this.withdrawReqAt, requireNonNull(current), 30);

        this.status = UserStatus.WITHDRAWN;
    }

    //to-do : Domain Service scheduling
    public boolean checkDeleteProperty(LocalDateTime current) {
        checkProperty(UserStatus.WITHDRAWN, this.withdrawReqAt, requireNonNull(current), 90);
        return true;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    // check condition for change status (time && status)
    private void checkProperty(UserStatus status
                    , LocalDateTime baseDate
                    , LocalDateTime current
                    , long dateDiff) {
        state(this.status == status, "status 변환 조건이 맞지 않습니다. 현재 상태값 = " + this.status);

        state(baseDate != null, "비교 날짜가 Null 입니다.");
        state(baseDate.isBefore(current.minusDays(dateDiff)), "경과 기간 조건이 맞지 않습니다");
    }
}

