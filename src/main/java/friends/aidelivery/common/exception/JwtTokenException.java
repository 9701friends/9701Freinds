package friends.aidelivery.common.exception;

public class JwtTokenException extends CustomUnauthorizedException {

    public JwtTokenException(int code, String message) {
        super(code, message);
    }
}