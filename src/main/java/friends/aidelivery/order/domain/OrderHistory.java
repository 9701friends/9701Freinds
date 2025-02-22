package friends.aidelivery.order.domain;


import friends.aidelivery.common.domain.TimeStamp;
import friends.aidelivery.order.domain.enums.OrderStatus;
import friends.aidelivery.order.domain.enums.OrderType;
import friends.aidelivery.order.exception.OrderHistoryForbiddenException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_history")
@Comment("주문 완료시 생성되는 주문내역 조회용 테이블")
@Entity
public class OrderHistory extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_history_id")
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_number", nullable = false)
    private String storeNumber;

    @Column(name = "store_address", nullable = false)
    private String storeAddress;

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

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductHistory> orderProductHistoryList;

    private OrderHistory(
        final UUID orderId,
        final Long userId,
        final UUID storeId,
        final String storeName,
        final String storeNumber,
        final String storeAddress,
        final OrderType orderType,
        final OrderStatus orderStatus,
        final Long totalPrice,
        final String orderAddress,
        final String comment,
        final LocalDateTime orderTime,
        final LocalDateTime completionTime,
        final List<OrderProduct> orderProducts) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.storeNumber = storeNumber;
        this.storeAddress = storeAddress;
        this.userId = userId;
        this.storeId = storeId;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderAddress = orderAddress;
        this.comment = comment;
        this.orderTime = orderTime;
        this.completionTime = completionTime;
        this.orderProductHistoryList = createOrderProductHistories(orderProducts);
    }

    public static OrderHistory create(final Order order) {
        return new OrderHistory(
            order.getId(),
            order.getUser().getId(),
            order.getStore().getId(),
            order.getStore().getName().getValue(),
            order.getStore().getStoreNumber().getValue(),
            order.getStore().getAddress().getValue(),
            order.getOrderType(),
            order.getOrderStatus(),
            order.getTotalPrice(),
            order.getOrderAddress(),
            order.getComment(),
            order.getOrderTime(),
            order.getCompletionTime(),
            order.getOrderProducts());
    }

    private List<OrderProductHistory> createOrderProductHistories(
        final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
            .map(orderProduct -> OrderProductHistory.create(this, orderProduct))
            .collect(Collectors.toList());
    }

    public void validateCustomer(final Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new OrderHistoryForbiddenException();
        }
    }
}
