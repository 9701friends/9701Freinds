package friends.aidelivery.order.domain.enums;

public enum OrderStatus {
    // 상태 변경 시간 순
    PAYMENT_PENDING,
    PAYMENT_COMPLETED,
    ORDER_PENDING,
    PROCESSING,
    DELIVERING,
    DELIVERY_COMPLETED,
    ORDER_COMPLETED,
    ORDER_CANCELED
}
