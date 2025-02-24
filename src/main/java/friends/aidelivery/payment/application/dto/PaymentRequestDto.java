package friends.aidelivery.payment.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentRequestDto {
    private Long user_id;
    private UUID order_id;
    private Double payment;
    private UUID payment_id;
}
