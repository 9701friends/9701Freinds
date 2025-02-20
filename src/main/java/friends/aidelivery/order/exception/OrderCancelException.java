package friends.aidelivery.order.exception;

public class OrderCancelException extends RuntimeException {

    public OrderCancelException(long allowed) {
        super(String.format("주문 후 %d분이 지난 주문은 취소할 수 없습니다.", allowed));
    }
}
