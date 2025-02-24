package friends.aidelivery.payment.domain.repository;

import friends.aidelivery.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    //Optional<Payment> findByUserIdAndOrderIdAndPaymentAndPaymentId(Long userId, UUID orderId, Double payment, UUID paymentId);

    Optional<Payment> findById(UUID paymentId);

    // userId로 결제 모든 내역 조회
    List<Payment> findByUserId(Long userId);
    
    //paymentId와 userID로 해당하는 하나의 결제 조회
    Optional<Payment> findByPaymentIdAndUserId(UUID paymentId, Long userId);
}
