package friends.aidelivery.order.application.dto.request;

import friends.aidelivery.order.domain.enums.OrderType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderCreateRequest(UUID storeId, OrderType orderType, String orderAddress,
                                 String comment,
                                 LocalDateTime orderTime,
                                 List<OrderProductRequest> orderProductRequests) {

    public List<UUID> getProductIds() {
        return orderProductRequests.stream()
            .map(OrderProductRequest::productId)
            .toList();
    }

    public Map<UUID, Integer> getQuantityMap() {
        return orderProductRequests.stream()
            .collect(
                Collectors.toMap(OrderProductRequest::productId, OrderProductRequest::quantity));
    }
}
