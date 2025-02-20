package friends.aidelivery.payment.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "p_payment")
public class Payment { //db에 접근

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "payment_state")
    private String paymentState;

    @Column(name = "payment")
    private Double payment;

    @Column(name = "user_id")
    private Long userId;

    @JsonProperty("order_id")
    @Column(name = "orderId")
    private UUID orderId;

    //결제 생성자
    public Payment(long userId, UUID orderId, Double payment, String paymentState) {
        this.userId = userId;
        this.orderId = orderId;
        this.payment = payment;
        this.paymentState = paymentState;
    }

    // 결제 승인 처리 메소드
    public void updatePaymentState(String paymentState) {
        this.paymentState = paymentState;
    }
}

