package friends.aidelivery.common.exception;

import friends.aidelivery.common.exception.code.CommonResultCode;
import lombok.Getter;

@Getter
public abstract class CustomBadRequestException extends RuntimeException {

    private final int code;

    protected CustomBadRequestException() {
        super(CommonResultCode.BAD_REQUEST.message());
        this.code = CommonResultCode.BAD_REQUEST.code();
    }

    protected CustomBadRequestException(String message) {
        super(message);
        this.code = CommonResultCode.BAD_REQUEST.code();
    }

    protected CustomBadRequestException(int code, String message) {
        super(message);
        this.code = code;
    }

    protected CustomBadRequestException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}