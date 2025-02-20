package friends.aidelivery.order.domain;

import friends.aidelivery.order.application.dto.request.OrderCreateRequest;
import friends.aidelivery.order.domain.enums.OrderStatus;
import friends.aidelivery.order.domain.enums.OrderType;
import friends.aidelivery.order.exception.OrderCancelException;
import friends.aidelivery.order.exception.OrderNotCompletedException;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "address")
    private String orderAddress;

    @Column(name = "comment")
    private String comment;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "completion_time")
    private LocalDateTime completionTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    private Order(final User user, final Store store, final OrderType orderType,
        final String orderAddress,
        final String comment, final LocalDateTime orderTime, final Long totalPrice,
        final OrderStatus orderStatus,
        final Map<Product, Integer> productQuantityMap) {
        this.user = user;
        this.store = store;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.orderAddress = orderAddress;
        this.comment = comment;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.orderProducts = createOrderProducts(productQuantityMap);
    }

    public static Order create(
        final User user,
        final Store store,
        final OrderCreateRequest request,
        final Long totalPrice,
        final Map<Product, Integer> productQuantityMap
    ) {
        return new Order(user, store, request.orderType(), request.orderAddress(),
            request.comment(), request.orderTime(), totalPrice, OrderStatus.PENDING,
            productQuantityMap);
    }

    private List<OrderProduct> createOrderProducts(final Map<Product, Integer> productQuantityMap) {
        return productQuantityMap.entrySet().stream()
            .map(entry ->
                OrderProduct.create(this, entry.getKey(), entry.getValue()))
            .toList();
    }

    public void checkOrderCompleted() {
        if (this.orderStatus != OrderStatus.COMPLETED) {
            throw new OrderNotCompletedException(this.id);
        }
        if (this.completionTime == null) {
            throw new OrderNotCompletedException(this.id);
        }
    }

    public void cancelOrder(final Long userId, final LocalDateTime cancelTime) {
        validateCustomer(userId);
        validateCancel(cancelTime);
        this.orderStatus = OrderStatus.CANCELED;
    }

    private void validateCancel(final LocalDateTime cancelTime) {
        final long allowed = 5;
        if (this.orderTime.isBefore(cancelTime.minusMinutes(allowed))) {
            throw new OrderCancelException(allowed);
        }
    }

    public void acceptOrder(final Long userId) {
        this.orderStatus = OrderStatus.PENDING;
    }

    public void rejectOrder(final Long userId) {
        this.orderStatus = OrderStatus.REJECTED;
    }

    private void validateCustomer(final Long userId) {
        if (!Objects.equals(user.getId(), userId)) {
            throw new OrderNotCompletedException(this.id);
        }
    }

    private void validateOwner(final String email) {
        // todo 가게주인과 맞는지 검증 로직 필요
    }
}
