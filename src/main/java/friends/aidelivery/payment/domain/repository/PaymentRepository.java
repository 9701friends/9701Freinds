package friends.aidelivery.payment.domain.repository;

import friends.aidelivery.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    //Optional<Payments> findByPaymentIdx(UUID paymentIdx);
}