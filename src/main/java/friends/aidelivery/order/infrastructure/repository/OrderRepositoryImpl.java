package friends.aidelivery.order.infrastructure.repository;

import friends.aidelivery.order.domain.Order;
import friends.aidelivery.order.domain.repository.OrderRepository;
import friends.aidelivery.order.infrastructure.jpa.OrderJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    @Override
    public Order save(Order order) {
        return jpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id);
    }
}
