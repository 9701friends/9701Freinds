package friends.aidelivery.order.application.dto.request;

import java.time.LocalDateTime;

public record OrderCancelRequest(LocalDateTime cancelTime) {

}
