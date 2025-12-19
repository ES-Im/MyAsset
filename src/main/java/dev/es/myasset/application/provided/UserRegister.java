package dev.es.myasset.application.provided;

import dev.es.myasset.domain.User;

public interface UserRegister {
    User registerFromOAuth(OAuthUserInfo OAuthUserInfo);
}
