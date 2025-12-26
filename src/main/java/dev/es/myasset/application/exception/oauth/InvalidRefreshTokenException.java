
package dev.es.myasset.application.exception.oauth;

public class InvalidRefreshTokenException extends AbstractAuthException {

    public InvalidRefreshTokenException() {
        super(AuthErrorCode.INVALID_REGISTER_TOKEN);

    }

}
