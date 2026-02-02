package dev.es.myasset.domain.shared;

import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="mail_preference")
public class MailPreference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long mailPreferenceId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(STRING)
    @Column(nullable = false)
    private MailType mailType;

    @Column(nullable = false)
    private Boolean isAgree;


}
