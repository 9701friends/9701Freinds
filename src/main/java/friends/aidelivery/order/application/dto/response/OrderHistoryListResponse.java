package friends.aidelivery.order.application.dto.response;

import friends.aidelivery.order.domain.OrderHistory;
import java.util.List;
import org.springframework.data.domain.Page;

public record OrderHistoryListResponse(List<OrderHistoryResponse> orderHistoryResponseList,
                                       int totalPages,
                                       long totalElements) {

    public static OrderHistoryListResponse of(Page<OrderHistory> orderHistories) {
        return new OrderHistoryListResponse(
            orderHistories.stream().map(OrderHistoryResponse::of).toList(),
            orderHistories.getTotalPages(),
            orderHistories.getTotalElements());
    }
}
