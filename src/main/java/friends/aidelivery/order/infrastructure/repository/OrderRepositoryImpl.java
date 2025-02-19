package friends.aidelivery.order.infrastructure.repository;

import friends.aidelivery.order.domain.Order;
import friends.aidelivery.order.domain.repository.OrderRepository;
import friends.aidelivery.order.infrastructure.jpa.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository JpaRepository;

    @Override
    public Order save(Order order) {
        return JpaRepository.save(order);
    }
}
