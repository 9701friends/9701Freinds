package friends.aidelivery.payments.domain;


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
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_idx")
    private UUID paymentIdx;

    @Column(name = "paymentState")
    private String paymentState;

    @Column(name = "payment")
    private Double payment;

    @Column(name = "userId")
    private String userId;

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

    public void setCreatedAt(LocalDateTime now) {
        this.created_at = created_at;
    }

    public void setCreatedBy(String createdBy) {
        this.created_by = createdBy;
    }

    public void setUpdatedAt(LocalDateTime now) {
        this.updated_at = updated_at;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updated_by = updatedBy;
    }
}

