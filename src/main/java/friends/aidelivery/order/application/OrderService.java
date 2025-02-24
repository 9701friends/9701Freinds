package friends.aidelivery.order.application;

import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.order.application.dto.request.OrderCancelRequest;
import friends.aidelivery.order.application.dto.request.OrderCreateRequest;
import friends.aidelivery.order.application.dto.response.OrderResponse;
import friends.aidelivery.order.domain.Order;
import friends.aidelivery.order.domain.enums.OrderStatus;
import friends.aidelivery.order.domain.repository.OrderRepository;
import friends.aidelivery.order.exception.OrderNotFoundException;
import friends.aidelivery.product.application.ProductService;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.store.exception.StoreNotFoundException;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;
    private final OrderHistoryService orderHistoryService;

    @Transactional
    public OrderResponse createOrder(final OrderCreateRequest request,
        final UserDetailsImpl userDetails) {

        final Long userId = userDetails.getUserId();
        final User user = userService.getUserOrElseThrow(userId);

        final UUID storeId = request.storeId();
        final Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(storeId));

        final Map<Product, Integer> productQuantityMap = findProductQuantityMap(request);

        final Long totalPrice = calculateTotalPrice(productQuantityMap);

        final Order order = Order.create(user, store, request, totalPrice, productQuantityMap);
        final Order saved = orderRepository.save(order);

        return OrderResponse.of(saved);
    }

    private Map<Product, Integer> findProductQuantityMap(final OrderCreateRequest request) {
        final List<UUID> productIds = request.getProductIds();
        final Map<UUID, Integer> quantityMap = request.getQuantityMap();

        final List<Product> products = findAllByProductIds(productIds);

        return products.stream()
            .collect(Collectors.toMap(
                product -> product,
                product -> quantityMap.get(product.getId())
            ));
    }

    private List<Product> findAllByProductIds(final List<UUID> productIds) {
        return productService.findAllByProductIds(productIds);
    }

    private Long calculateTotalPrice(final Map<Product, Integer> productQuantityMap) {
        return productQuantityMap.keySet().stream()
            .mapToLong(product -> {
                Long price = product.getPrice().getValue();
                Integer quantity = productQuantityMap.get(product);
                return calculatePrice(price, quantity);
            }).sum();
    }

    private Long calculatePrice(final Long price, final Integer quantity) {
        return price * quantity;
    }

    public OrderResponse getOrderById(final UUID orderId) {
        final Order order = getOrderOrThrow(orderId);
        return OrderResponse.of(order);
    }

    @Transactional
    public void cancelOrder(final UUID orderId, final UserDetailsImpl userDetails,
        final OrderCancelRequest request) {
        final Long userId = userDetails.getUserId();
        final User user = userService.getUserOrElseThrow(userId);
        final Order order = getOrderOrThrow(orderId);
        order.cancelOrder(user.getId(), request.cancelTime());
    }

    public Order getOrderOrThrow(final UUID orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public void updateOrderStatus(final UUID orderId, final UserDetailsImpl userDetails, final
    OrderStatus status) {
        final User user = userService.getUserOrElseThrow(userDetails.getUserId());
        final Order order = getOrderOrThrow(orderId);
        order.updateOrderStatus(user.getId(), status);
    }

    @Transactional
    public void completeOrder(final UUID orderId, final UserDetailsImpl userDetails) {
        final Long userId = userDetails.getUserId();
        final User user = userService.getUserOrElseThrow(userId);
        final Order order = getOrderOrThrow(orderId);
        order.completeOrder(user.getId());
        orderHistoryService.saveOrderHistory(order);
    }
}
