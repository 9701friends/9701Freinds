package friends.aidelivery.payment.exception;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(String.format("Unauthorized access: %s", message));
    }
}
