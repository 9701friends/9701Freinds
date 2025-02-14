package friends.aidelivery.common.exception;

import friends.aidelivery.common.exception.code.CommonResultCode;
import lombok.Getter;

@Getter
public abstract class CustomUnauthorizedException extends RuntimeException {

    private final int code;

    protected CustomUnauthorizedException() {
        super(CommonResultCode.UNAUTHORIZED.message());
        this.code = CommonResultCode.UNAUTHORIZED.code();
    }

    protected CustomUnauthorizedException(String message) {
        super(message);
        this.code = CommonResultCode.UNAUTHORIZED.code();
    }

    protected CustomUnauthorizedException(int code, String message) {
        super(message);
        this.code = code;
    }

    protected CustomUnauthorizedException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
