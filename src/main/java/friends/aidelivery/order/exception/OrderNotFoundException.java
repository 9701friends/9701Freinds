package friends.aidelivery.order.exception;

import friends.aidelivery.common.exception.CustomNotFoundException;
import java.util.UUID;

public class OrderNotFoundException extends CustomNotFoundException {

    public OrderNotFoundException(final UUID id) {
        super(String.format(
            "조회한 주문이 존재하지 않습니다. - 요청 정보 { orderId: %s}",
            id.toString()));
    }
}
