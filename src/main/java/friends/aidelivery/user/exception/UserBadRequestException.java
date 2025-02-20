package friends.aidelivery.user.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class UserBadRequestException extends CustomBadRequestException {

    public UserBadRequestException(int code, String message) {
        super(code, message);
    }
}
