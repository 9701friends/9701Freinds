package friends.aidelivery.payment.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;

import java.util.UUID;

public class PaymentNotFoundException extends CustomNotFoundException {

    public PaymentNotFoundException(final UUID paymentId) {
        super(String.format(
                "조회한 결제가 존재하지 않습니다. - 요청 정보 { paymentId: %s }",
                paymentId.toString()));
    }
}