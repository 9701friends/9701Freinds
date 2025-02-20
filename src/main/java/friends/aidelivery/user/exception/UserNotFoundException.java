package friends.aidelivery.user.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;

public class UserNotFoundException extends CustomNotFoundException {

    public UserNotFoundException() {
        super(594, "유저를 찾을 수 없습니다.");
    }
}
