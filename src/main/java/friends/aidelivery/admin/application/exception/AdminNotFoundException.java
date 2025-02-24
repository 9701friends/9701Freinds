package friends.aidelivery.admin.application.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;

public class AdminNotFoundException extends CustomNotFoundException {

    public AdminNotFoundException(int code, String message) {
        super(code, message);
    }
}
