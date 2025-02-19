package friends.aidelivery.order.domain.repository;

import friends.aidelivery.order.domain.Order;

public interface OrderRepository {

    Order save(Order order);
}
