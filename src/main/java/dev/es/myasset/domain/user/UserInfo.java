package dev.es.myasset.domain.user;

import dev.es.myasset.domain.shared.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

@Entity
@Table
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class UserInfo {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String userKey;

    private String username;

    @Embedded
    @NaturalId
    private Email email;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    public static UserInfo registerFromOAuth(String username,
                                             ProviderType providerType,
                                             String email) {
        UserInfo userInfo = new UserInfo();

        userInfo.username = username;
        userInfo.providerType = providerType;
        userInfo.email = new Email(email);

        return userInfo;
    }

}
