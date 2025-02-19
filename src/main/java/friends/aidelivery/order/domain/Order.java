package friends.aidelivery.order.domain;

import friends.aidelivery.order.application.dto.request.OrderCreateRequest;
import friends.aidelivery.order.domain.enums.OrderStatus;
import friends.aidelivery.order.domain.enums.OrderType;
import friends.aidelivery.product.domain.Product;
import friends.aidelivery.store.domain.Store;
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

    @Column(name = "user_id")
    private Long userId;

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

    private Order(final Long userId, final Store store, final OrderType orderType,
        final String orderAddress,
        final String comment, final LocalDateTime orderTime, final Long totalPrice,
        final OrderStatus orderStatus,
        final Map<Product, Integer> productQuantityMap) {
        this.userId = userId;
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
        final Long userId,
        final Store store,
        final OrderCreateRequest request,
        final Long totalPrice,
        final Map<Product, Integer> productQuantityMap
    ) {
        return new Order(userId, store, request.orderType(), request.orderAddress(),
            request.comment(), request.orderTime(), totalPrice, OrderStatus.PAYMENT_PENDING,
            productQuantityMap);
    }

    private List<OrderProduct> createOrderProducts(final Map<Product, Integer> productQuantityMap) {
        return productQuantityMap.entrySet().stream()
            .map(entry ->
                OrderProduct.create(this, entry.getKey(), entry.getValue()))
            .toList();
    }

}
