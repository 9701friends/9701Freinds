package friends.aidelivery.order.exception;

import java.util.UUID;

public class OrderHistoryNotFoundException extends RuntimeException {

    public OrderHistoryNotFoundException(UUID orderId) {
        super(String.format(
            "조회한 주문내역이 존재하지 않습니다. - 요청 정보 { orderId: %s}",
            orderId.toString()));
    }
}
