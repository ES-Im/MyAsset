
package dev.es.myasset.application.exception.oauth;

public class ExpiredRefreshTokenException extends AbstractAuthException {

    public ExpiredRefreshTokenException() {
        super(AuthErrorCode.EXPIRED_REFRESH_TOKEN);

    }

}
