package dev.es.myasset.domain.notification;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_key", "notiType"})
        , name = "notification_rule"
)
public class NotificationRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiRuleId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_key", nullable = false)
    private User user;  // default = 시스템 디폴트 설정

    @Enumerated(STRING)
    @Column(nullable = false)
    private NotificationType notiType;

    // 유저가 객체 생성 시 : 알림 비활성화 설정
    public static NotificationRule createUserBlockRule(User user, NotificationType notiType) {
        NotificationRule notificationRule = new NotificationRule();

        notificationRule.user = requireNonNull(user);
        notificationRule.notiType = requireNonNull(notiType);

        return notificationRule;
    }

    // 관리자가 객체 생성 시 : 디폴트 알림 설정
    public static NotificationRule createDefaultRule(NotificationType notiType) {
        NotificationRule notificationRule = new NotificationRule();
        notificationRule.notiType = requireNonNull(notiType);

        return notificationRule;
    }


}
