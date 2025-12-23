package dev.es.myasset.adapter.security.token;

import org.springframework.beans.factory.annotation.Value;
import java.time.Duration;

public class JwtExpirationProperties {
    @Value("${JWT_REGISTER_TOKEN_EXPIRATION_TIME}")
    private Duration registerTokenExpirationTime;

    @Value("${JWT_ACCESS_TOKEN_EXPIRATION_TIME}")
    private Duration accessTokenExpirationTime;

    @Value("${JWT_REFRESH_TOKEN_EXPIRATION_TIME}")
    private Duration refreshTokenExpirationTime;

    public long registerTokenExpirationTimeToMillis() {
        return this.registerTokenExpirationTime.toMillis();
    }

    public long accessTokenExpirationTimeToMillis() {
        return this.accessTokenExpirationTime.toMillis();
    }

    public long refreshTokenExpirationTimeToMillis() {
        return this.refreshTokenExpirationTime.toMillis();
    }
}
