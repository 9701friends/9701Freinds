package friends.aidelivery.payment.domain.repository;

import friends.aidelivery.payment.domain.Payment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByOrderId(UUID orderId);

    Page<Payment> findByUserId(Long userId, Pageable pageable);

    Page<Payment> findByStoreId(UUID storeId, Pageable pageable);
}
