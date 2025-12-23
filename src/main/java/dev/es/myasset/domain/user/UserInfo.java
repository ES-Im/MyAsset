package dev.es.myasset.domain.user;

import dev.es.myasset.domain.shared.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

import static java.util.Objects.*;

@Entity
@Table
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id
    @Column(name="user_key")
    private String userKey;

    private String providerId;

    private String username;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    public static UserInfo registerUserInfo(String providerType,
                                            String providerId,
                                            String email,
                                            String username) {
        UserInfo userInfo = new UserInfo();

        userInfo.userKey = UUID.randomUUID().toString();
        userInfo.providerType = requireNonNull(ProviderType.from(providerType));
        userInfo.providerId = requireNonNull(providerId);
        userInfo.email = new Email(email);
        userInfo.username = requireNonNull(username);

        return userInfo;
    }

//    public static UserInfo nullifyUserInfo(UserInfo userInfo) {
//        userInfo.username = null;
//        userInfo.email = null;
//        userInfo.providerType = null;
//
//        return  userInfo;
//    }

}
