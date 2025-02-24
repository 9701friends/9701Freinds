package friends.aidelivery.order.exception;

import friends.aidelivery.common.exception.CustomForbiddenException;

public class OrderHistoryForbiddenException extends CustomForbiddenException {

    public OrderHistoryForbiddenException() {
        super("해당 주문내역에 대한 권한이 없습니다.");

    }
}
