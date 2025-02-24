package friends.aidelivery.order.exception;

import friends.aidelivery.common.exception.CustomBadRequestException;
import java.util.UUID;

public class OrderNotCompletedException extends CustomBadRequestException {

    public OrderNotCompletedException(final UUID id) {
        super(String.format(
            "해당 주문이 완료되지 않았습니다. - 요청 정보 { orderId: %s}",
            id.toString()));
    }
}
