package dev.es.myasset.domain.user;

import dev.es.myasset.domain.shared.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.UUID;

@Entity
@Table
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
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

    public static UserInfo registerFromOAuth(String username,
                                             ProviderType providerType,
                                             String email) {
        UserInfo userInfo = new UserInfo();

        userInfo.userKey = UUID.randomUUID().toString();
        userInfo.username = username;
        userInfo.providerType = providerType;
        userInfo.email = new Email(email);

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
