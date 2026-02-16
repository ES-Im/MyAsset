package dev.es.myasset.domain.notification;

import dev.es.myasset.domain.shared.NonAuditingEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.springframework.util.Assert.state;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "notification")
public class Notification extends NonAuditingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long notiId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_key", nullable = false)
    private User user;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable = false)
    private Boolean isHide;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public static Notification createNotification(User user, LocalDateTime sentTime) {
        Notification notification = new Notification();

        notification.user = user;
        notification.isRead = false;
        notification.isHide = false;

        notification.sentAt = sentTime;

        return notification;
    }

    public void markHide() {
        state(!this.isHide, "이미 숨김처리된 알림입니다.");

        this.isHide = true;
    }

}
