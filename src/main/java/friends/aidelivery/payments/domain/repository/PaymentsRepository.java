package friends.aidelivery.payments.domain.repository;

import friends.aidelivery.payments.domain.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
    //Optional<Payments> findByPaymentIdx(UUID paymentIdx);
}