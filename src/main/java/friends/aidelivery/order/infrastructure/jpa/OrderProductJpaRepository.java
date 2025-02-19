package friends.aidelivery.order.infrastructure.jpa;

import friends.aidelivery.order.domain.OrderProduct;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, UUID> {

}
