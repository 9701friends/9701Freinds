package friends.aidelivery.order.application.dto.request;

import java.util.UUID;

public record OrderProductRequest(UUID productId, Integer quantity) {

}
