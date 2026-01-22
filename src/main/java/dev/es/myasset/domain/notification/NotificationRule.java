package dev.es.myasset.domain.notification;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "notification_rule")
public class NotificationRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiRuleId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_key", nullable = true)
    private User user;

    @Enumerated(STRING)
    private NotificationType notiType;

    private Boolean isActive;

    private Boolean isHide;

}
