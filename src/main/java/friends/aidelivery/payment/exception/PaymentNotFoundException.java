package friends.aidelivery.payment.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;
import java.util.UUID;

public class PaymentNotFoundException extends CustomNotFoundException {

    public PaymentNotFoundException(final UUID orderId) {
        super(String.format(
            "해당 주문의 결제내역이 존재하지 않습니다. - 요청 정보 { orderId: %s }",
            orderId.toString()));
    }
}