package friends.aidelivery.payment.application.dto;

import java.util.UUID;

public record PaymentRequest(UUID orderId, Long price, UUID storeId) {

}
