package friends.aidelivery.user.domain.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    public MemberException(String message) {
        super(message);
    }
}