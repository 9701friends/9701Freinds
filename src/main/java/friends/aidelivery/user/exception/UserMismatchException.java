package friends.aidelivery.user.exception;

import friends.aidelivery.common.exception.CustomUnauthorizedException;

public class UserMismatchException extends CustomUnauthorizedException {

    public UserMismatchException() {
        super(594, "로그인 된 정보와 확인하려고 하는 유저의 정보가 다릅니다.");
    }
}
