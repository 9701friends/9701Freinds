package friends.aidelivery.order.application;

import friends.aidelivery.order.application.dto.request.OrderCancelRequest;
import friends.aidelivery.order.application.dto.request.OrderCreateRequest;
import friends.aidelivery.order.application.dto.response.OrderResponse;
import friends.aidelivery.order.domain.Order;
import friends.aidelivery.order.domain.repository.OrderProductRepository;
import friends.aidelivery.order.domain.repository.OrderRepository;
import friends.aidelivery.order.exception.OrderForbiddenException;
import friends.aidelivery.order.exception.OrderNotFoundException;
import friends.aidelivery.product.application.ProductService;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.store.exception.StoreNotFoundException;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.domain.vo.Email;
import jakarta.persistence.EntityNotFoundException;
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
    private final OrderProductRepository orderProductRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse createOrder(final OrderCreateRequest request, Email email) {

        /*
        주문 생성 흐름
        1. 유저 검증
        2. 가게 검증
        3. 상품 검증
        4. totalPrice 계산
        5. order 저장
        6. orderProduct 저장
         */

        // todo 1. 유저 검증
        final User user = userRepository.findByEmail(email)
            .orElseThrow(EntityNotFoundException::new);

        // 가게 검증
        final UUID storeId = request.storeId();
        final Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(storeId));

        // 상품 검증 -> 상품,수량 Map 생성
        final Map<Product, Integer> productQuantityMap = findProductQuantityMap(request);

        // totalPrice 계산
        final Long totalPrice = calculateTotalPrice(productQuantityMap);

        // 주문 저장 (초기 상태: 결제 대기)
        Order order = Order.create(user, store, request, totalPrice, productQuantityMap);
        Order saved = orderRepository.save(order);

        return OrderResponse.of(saved);

    }

    private Map<Product, Integer> findProductQuantityMap(final OrderCreateRequest request) {
        List<UUID> productIds = request.getProductIds();
        Map<UUID, Integer> quantityMap = request.getQuantityMap();

        List<Product> products = findAllByProductIds(productIds);

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

    public Order validateOrderForReview(final UUID orderId) {
        Order order = findOrderOrThrow(orderId);
        order.checkOrderCompleted();
        return order;
    }

    public OrderResponse getOrderById(final UUID orderId) {
        Order order = findOrderOrThrow(orderId);
        return OrderResponse.of(order);
    }

    @Transactional
    public void cancelOrder(final UUID orderId, final Email email,
        final OrderCancelRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Order order = findOrderOrThrow(orderId);
        order.cancelOrder(user.getId(), request.cancelTime());
    }

    private Order findOrderOrThrow(final UUID orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public void acceptOrder(final UUID orderId, final Email email) {
        // userService -> owner 권한 검증
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            throw new OrderForbiddenException(orderId);
        }
        // todo storeService -> 주문한 가게주인이 맞는지 검증
        Order order = findOrderOrThrow(orderId);
        order.acceptOrder(user.getId());
    }

    @Transactional
    public void rejectOrder(final UUID orderId, final Email email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            throw new OrderForbiddenException(orderId);
        }
        Order order = findOrderOrThrow(orderId);
        order.rejectOrder(user.getId());
    }
}
