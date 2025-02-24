package friends.aidelivery.order.domain.repository;

import friends.aidelivery.order.domain.OrderHistory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderHistoryRepository {

    OrderHistory save(OrderHistory orderHistory);

    Optional<OrderHistory> findById(UUID orderHistoryId);

    Page<OrderHistory> search(Long userId, Pageable pageable, String search);

    Page<OrderHistory> findAllByStoreId(UUID storeId, Pageable pageable);
}
