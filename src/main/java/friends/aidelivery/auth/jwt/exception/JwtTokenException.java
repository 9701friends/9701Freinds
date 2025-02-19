package friends.aidelivery.auth.jwt.exception;

import friends.aidelivery.common.exception.CustomUnauthorizedException;

public class JwtTokenException extends CustomUnauthorizedException {

    public JwtTokenException(int code, String message) {
        super(code, message);
    }
}