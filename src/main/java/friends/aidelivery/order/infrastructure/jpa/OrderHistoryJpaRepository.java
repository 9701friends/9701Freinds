package friends.aidelivery.order.infrastructure.jpa;

import friends.aidelivery.order.domain.OrderHistory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryJpaRepository extends JpaRepository<OrderHistory, UUID> {

    Optional<OrderHistory> findByOrderId(UUID orderId);

    Page<OrderHistory> findAllByStoreId(UUID storeId, Pageable pageable);

}
