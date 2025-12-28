
package dev.es.myasset.application.exception.oauth;

import dev.es.myasset.application.exception.ErrorCode;

public class InvalidRefreshTokenException extends AbstractAuthException {

    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REGISTER_TOKEN);

    }

}
