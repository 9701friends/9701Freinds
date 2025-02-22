package friends.aidelivery.review.infrastructure.jpa;

import friends.aidelivery.review.domain.Review;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, UUID> {

    boolean existsByOrderHistoryId(UUID orderHistoryId);

    Page<Review> findAllByStoreId(UUID storeId, Pageable pageable);

    Page<Review> findAllByUserId(Long userId, Pageable pageable);
}
