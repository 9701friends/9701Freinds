package friends.aidelivery.order.domain.repository;

import friends.aidelivery.order.domain.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);
}
