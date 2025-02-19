package friends.aidelivery.common.exception;

import friends.aidelivery.common.exception.code.CommonResultCode;
import lombok.Getter;

@Getter
public abstract class CustomForbiddenException extends RuntimeException {

    private final int code;

    protected CustomForbiddenException() {
        super(CommonResultCode.FORBIDDEN.message());
        this.code = CommonResultCode.FORBIDDEN.code();
    }

    protected CustomForbiddenException(String message) {
        super(message);
        this.code = CommonResultCode.FORBIDDEN.code();
    }

    protected CustomForbiddenException(int code, String message) {
        super(message);
        this.code = code;
    }

    protected CustomForbiddenException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
