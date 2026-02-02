package dev.es.myasset.domain.user;

import dev.es.myasset.domain.shared.Email;
import dev.es.myasset.domain.shared.NonAuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.*;

@Entity
@Getter
@ToString(exclude = {"user"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"provider_type", "provider_id"} ),
        name = "user_info")
public class UserInfo extends NonAuditingEntity {

    @Id
    private String userKey;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", unique = true)
    private User user;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String username;

    @Embedded
    @Column(nullable = false)
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType providerType;

    public void linkUser(User user) {
        this.user = requireNonNull(user);
    }

    public static UserInfo registerUserInfo(String providerType,
                                            String providerId,
                                            String email,
                                            String username) {
        UserInfo userInfo = new UserInfo();

        userInfo.providerType = requireNonNull(ProviderType.from(providerType));
        userInfo.providerId = requireNonNull(providerId);
        userInfo.email = new Email(email);
        userInfo.username = requireNonNull(username);

        return userInfo;
    }

}
