package friends.aidelivery.order.exception;

import friends.aidelivery.common.exception.CustomForbiddenException;
import java.util.UUID;

public class OrderForbiddenException extends CustomForbiddenException {

    public OrderForbiddenException(final UUID id) {
        super(
            String.format("해당 주문에 대한 권한이 없습니다. - 요청 정보 { orderId: %s}",
                id.toString()));
    }
}
