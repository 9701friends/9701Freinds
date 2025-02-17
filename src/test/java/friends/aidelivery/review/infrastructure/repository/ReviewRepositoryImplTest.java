package friends.aidelivery.review.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import friends.aidelivery.review.domain.Review;
import friends.aidelivery.review.domain.vo.ReviewTime;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewRepositoryImplTest {

    @Autowired
    private ReviewRepositoryImpl reviewRepository;

    @DisplayName("리뷰 저장이 성공한다.")
    @Test
    void save_success() {
        // given
        final UUID orderId = UUID.randomUUID();
        final UUID userId = UUID.randomUUID();
        final String content = "리뷰 내용";
        final Integer rating = 5;
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime orderTime = now.minusDays(3);
        final ReviewTime reviewTime = new ReviewTime(now, orderTime);
        final Review review = new Review(orderId, userId, content, rating, reviewTime);
        // when
        Review saved = reviewRepository.save(review);
        // then
        assertThat(saved).isNotNull();
        assertThat(saved.getOrderId()).isEqualTo(orderId);
        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getContent().getValue()).isEqualTo(content);
        assertThat(saved.getRating().getValue()).isEqualTo(rating);
        assertThat(saved.getReviewTime()).isEqualTo(reviewTime);
    }

    @DisplayName("orderId로 중복되는 리뷰를 찾을 수 있다.")
    @Test
    void existsByOrderId_success() {
        // given
        final UUID orderId = UUID.randomUUID();
        final UUID userId = UUID.randomUUID();
        final String content = "리뷰 내용";
        final Integer rating = 5;
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime orderTime = now.minusDays(3);
        final ReviewTime reviewTime = new ReviewTime(now, orderTime);
        final Review review1 = new Review(orderId, userId, content, rating, reviewTime);
        reviewRepository.save(review1);
        // when
        boolean result = reviewRepository.existsByOrderId(orderId);
        // then
        assertThat(result).isTrue();
    }
}