package friends.aidelivery.payment.exception;

import java.util.UUID;

public class PaymentCheckException extends RuntimeException {

    public PaymentCheckException(final UUID paymentId) {
        super(String.format(
            "결제가 실패하였습니다. - 요청 정보 { paymentId: %s }",
            paymentId.toString()));
    }
}
