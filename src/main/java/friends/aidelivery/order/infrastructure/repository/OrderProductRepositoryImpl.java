package friends.aidelivery.order.infrastructure.repository;

import friends.aidelivery.order.domain.repository.OrderProductRepository;
import friends.aidelivery.order.infrastructure.jpa.OrderProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final OrderProductJpaRepository jpaRepository;
}
