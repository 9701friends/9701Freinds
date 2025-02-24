package friends.aidelivery.payment.application.dto;

import friends.aidelivery.payment.domain.Payment;
import friends.aidelivery.payment.domain.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(UUID paymentId, UUID orderId, Long price, PaymentStatus paymentStatus,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {

    public static PaymentResponse of(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getOrderId(), payment.getPrice(),
            payment.getStatus(),
            payment.getCreatedAt(), payment.getUpdatedAt());
    }
}
