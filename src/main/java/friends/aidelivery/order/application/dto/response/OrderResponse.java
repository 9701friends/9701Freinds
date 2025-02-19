package friends.aidelivery.order.application.dto.response;

import friends.aidelivery.order.domain.Order;
import friends.aidelivery.order.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(UUID orderId, String email, Long totalPrice, OrderStatus status,
                            LocalDateTime orderTime, LocalDateTime completionTime) {

    public static OrderResponse of(Order order) {
        return new OrderResponse(
            order.getId(), order.getUser().getEmail().getValue(), order.getTotalPrice(),
            order.getOrderStatus(), order.getOrderTime(), order.getCompletionTime());
    }
}
