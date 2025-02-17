package friends.aidelivery.common.exception;

import friends.aidelivery.common.exception.code.CommonResultCode;
import lombok.Getter;

@Getter
public abstract class CustomNotFoundException extends RuntimeException {

    private final int code;

    protected CustomNotFoundException() {
        super(CommonResultCode.DATA_NOT_FOUNT.message());
        this.code = CommonResultCode.DATA_NOT_FOUNT.code();
    }

    protected CustomNotFoundException(String message) {
        super(message);
        this.code = CommonResultCode.DATA_NOT_FOUNT.code();
    }

    protected CustomNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    protected CustomNotFoundException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
