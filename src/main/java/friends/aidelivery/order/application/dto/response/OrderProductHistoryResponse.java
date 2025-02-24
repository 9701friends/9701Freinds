package friends.aidelivery.order.application.dto.response;

import friends.aidelivery.order.domain.OrderProductHistory;

public record OrderProductHistoryResponse(
    String productName,
    String productContent,
    Long productPrice,
    int quantity
) {

    public static OrderProductHistoryResponse of(OrderProductHistory productHistory) {
        return new OrderProductHistoryResponse(
            productHistory.getProductName(),
            productHistory.getProductContent(),
            productHistory.getProductPrice(),
            productHistory.getQuantity());
    }
}
