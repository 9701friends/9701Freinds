package friends.aidelivery.user.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class UserPasswordMismatchException extends CustomBadRequestException {

    public UserPasswordMismatchException() {
        super(593, "유저의 패스워드가 일치하지 않습니다.");
    }
}
