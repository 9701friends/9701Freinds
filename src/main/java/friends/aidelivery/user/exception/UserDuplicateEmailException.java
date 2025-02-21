package friends.aidelivery.user.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class UserDuplicateEmailException extends CustomBadRequestException {

    public UserDuplicateEmailException() {
        super(521, "중복된 이메일입니다.");
    }
}
