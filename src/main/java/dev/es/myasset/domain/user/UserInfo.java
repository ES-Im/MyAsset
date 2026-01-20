package dev.es.myasset.domain.user;

import dev.es.myasset.domain.shared.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.*;

@Entity
@Table(name = "user_info")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id
    @Column(name = "user_key")
    private String userKey;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key")
    private User user;

    private String providerId;

    private String username;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    public static UserInfo registerUserInfo(User user,
                                            String providerType,
                                            String providerId,
                                            String email,
                                            String username) {
        UserInfo userInfo = new UserInfo();

        userInfo.userKey = user.getUserKey();
        userInfo.providerType = requireNonNull(ProviderType.from(providerType));
        userInfo.providerId = requireNonNull(providerId);
        userInfo.email = new Email(email);
        userInfo.username = requireNonNull(username);

        return userInfo;
    }

}
