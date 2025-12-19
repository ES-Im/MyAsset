package dev.es.myasset.domain;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class UserInfo {

    private String userKey;

    private String username;

    private ProviderType providerType;

    private String email;

    private UserInfo() {
    }

    public static UserInfo registerFromOAuth(String userKey,
                                             String username,
                                             ProviderType providerType,
                                             String email) {
        UserInfo userInfo = new UserInfo();

        userInfo.userKey = userKey;
        userInfo.username = username;
        userInfo.providerType = providerType;
        userInfo.email = email;

        return userInfo;
    }

}
