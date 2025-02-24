package friends.aidelivery.user.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;

public class UserDuplicatePhoneException extends CustomBadRequestException {

    public UserDuplicatePhoneException() {
        super(520, "중복된 휴대전화 번호입니다.");
    }
}
