package friends.aidelivery.order.infrastructure.jpa;

import friends.aidelivery.order.domain.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {

}
