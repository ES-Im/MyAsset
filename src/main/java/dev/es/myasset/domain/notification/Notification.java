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
    private LocalDateTime sentAt;

}
