package friends.aidelivery.user.domain.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;

public class UserNotFoundException extends CustomNotFoundException {

    public UserNotFoundException(int code, String message) {
        super(code, message);
    }
}
