package friends.aidelivery.user.exception;

import friends.aidelivery.common.exception.CustomUnauthorizedException;

public class UserUnauthorizedException extends CustomUnauthorizedException {

    public UserUnauthorizedException() {
        super("권한이 없습니다.");
    }
}
