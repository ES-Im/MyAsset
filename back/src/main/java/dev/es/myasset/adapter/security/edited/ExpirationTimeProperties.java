package dev.es.myasset.adapter.security.edited;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

/*
 * JWT와 Cookie 만료시간을 설정
 */
@Component
@Getter
public class ExpirationTimeProperties {

    @Value("${JWT_ACCESS_TOKEN_EXPIRATION_TIME}")
    private Duration accessTokenExpirationTime;

    @Value("${JWT_REFRESH_TOKEN_EXPIRATION_TIME}")
    private Duration refreshTokenExpirationTime;

    public long accessTokenExpirationMillis() {
        return this.accessTokenExpirationTime.toMillis();
    }

    public long refreshTokenExpirationMillis() {
        return this.refreshTokenExpirationTime.toMillis();
    }


    public int refreshCookieExpirationSeconds() {
        return (int) (this.refreshTokenExpirationMillis()) / 1000;
    }
}
