package friends.aidelivery.order.application.dto.response;

import friends.aidelivery.order.domain.Order;
import java.util.UUID;

public record OrderResponse(UUID orderId) {

    public static OrderResponse of(Order order) {
        return new OrderResponse(order.getId());
    }
}
