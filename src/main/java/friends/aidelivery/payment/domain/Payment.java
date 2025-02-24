package friends.aidelivery.payment.domain;

import friends.aidelivery.common.domain.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_payment")
public class Payment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "price")
    private Long price;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "store_id")
    private UUID storeId;

    public Payment(final Long userId, final UUID orderId, final Long price,
        final PaymentStatus status, final UUID storeId) {
        this.userId = userId;
        this.orderId = orderId;
        this.price = price;
        this.status = status;
        this.storeId = storeId;
    }

    public void updatePaymentState(final PaymentStatus status) {
        this.status = status;
    }
}