package friends.aidelivery.user.domain.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class MemberBadRequestException extends CustomBadRequestException {

    public MemberBadRequestException(int code, String message) {
        super(code, message);
    }
}
