package friends.aidelivery.order.application.dto.response;

import friends.aidelivery.order.domain.OrderHistory;
import friends.aidelivery.order.domain.enums.OrderType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderHistoryResponse(
    UUID orderId,
    Long userId,
    String storeName,
    String storeNumber,
    String storeAddress,
    OrderType orderType,
    Long totalPrice,
    String orderAddress,
    String comment,
    LocalDateTime orderTime,
    List<OrderProductHistoryResponse> orderProductHistoryResponseList
) {

    public static OrderHistoryResponse of(OrderHistory orderHistory) {
        return new OrderHistoryResponse(orderHistory.getOrderId(),
            orderHistory.getUserId(),
            orderHistory.getStoreName(),
            orderHistory.getStoreNumber(),
            orderHistory.getStoreAddress(),
            orderHistory.getOrderType(),
            orderHistory.getTotalPrice(),
            orderHistory.getOrderAddress(),
            orderHistory.getComment(),
            orderHistory.getOrderTime(),
            orderHistory.getOrderProductHistoryList().stream().map(OrderProductHistoryResponse::of)
                .toList()
        );
    }

}
