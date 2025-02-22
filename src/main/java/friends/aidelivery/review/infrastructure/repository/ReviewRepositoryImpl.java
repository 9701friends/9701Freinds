package friends.aidelivery.review.infrastructure.repository;

import friends.aidelivery.review.domain.Review;
import friends.aidelivery.review.domain.repository.ReviewRepository;
import friends.aidelivery.review.infrastructure.jpa.ReviewJpaRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository jpaRepository;

    @Override
    public Review save(Review review) {
        return jpaRepository.save(review);
    }

    @Override
    public boolean existsByOrderHistoryId(UUID orderHistoryId) {
        return jpaRepository.existsByOrderHistoryId(orderHistoryId);
    }

    @Override
    public Optional<Review> findById(UUID reviewId) {
        return jpaRepository.findById(reviewId);
    }

    @Override
    public Page<Review> findAllByStoreId(UUID storeId, Pageable pageable) {
        return jpaRepository.findAllByStoreId(storeId, pageable);
    }

    @Override
    public Page<Review> findAllByUserId(Long userId, Pageable pageable) {
        return jpaRepository.findAllByUserId(userId, pageable);
    }

}
