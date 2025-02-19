package friends.aidelivery.review.infrastructure.jpa;

import friends.aidelivery.review.domain.Review;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, UUID> {

    boolean existsByOrderId(UUID orderId);
}
