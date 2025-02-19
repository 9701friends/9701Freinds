package friends.aidelivery.payment.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_payment")
public class Payment { //db에 접근

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_idx")
    private UUID paymentIdx;

    @Column(name = "payment_state")
    private String paymentState;

    @Column(name = "payment")
    private Double payment;


    @Column(name = "user_id")
    private String userId;

    @JsonProperty("order_id")
    @Column(name = "orderId")
    private String orderId;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "created_by")
    private String created_by;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "updated_by")
    private String updated_by;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    @Column(name = "deleted_by")
    private String deleted_by;

    public Payment(String userId, String orderId, Double payment, String paymentState, LocalDateTime createdAt, String createdBy) {
        this.userId = userId;
        this.orderId = orderId;
        this.payment = payment;
        this.paymentState = paymentState;
        this.created_at = createdAt;
        this.created_by = createdBy;
    }

    public void setCreatedAt(LocalDateTime now) {this.created_at = now; }

    public void setCreatedBy(String createdBy) {
        this.created_by = createdBy;
    }

    public void setUpdatedAt(LocalDateTime now) {this.updated_at = now;
    }

    public void setUpdatedBy(String updatedBy) {this.updated_by = updatedBy;}

    public void setDeletedAt(LocalDateTime now) {this.deleted_at = now;}

    public void setDeletedBy(String deletedBy) {this.deleted_by = deletedBy;}
}

