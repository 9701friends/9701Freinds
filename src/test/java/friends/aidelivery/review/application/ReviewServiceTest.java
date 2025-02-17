package friends.aidelivery.review.application;

import static org.assertj.core.api.Assertions.assertThat;

import friends.aidelivery.review.application.dto.request.ReviewCreateRequest;
import friends.aidelivery.review.application.dto.response.ReviewResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @DisplayName("리뷰 생성이 성공한다.")
    @Test
    void createReview_success() {
        // given
        final UUID orderId = UUID.randomUUID();
        final String content = "리뷰 내용";
        final Integer rating = 5;
        final LocalDateTime reviewTime = LocalDateTime.now();
        final ReviewCreateRequest request = new ReviewCreateRequest(orderId, content, rating,
            reviewTime);
        // when
        ReviewResponse response = reviewService.createReview(request);
        // then
        assertThat(response).isNotNull();
        assertThat(response.orderId()).isEqualTo(orderId);
        assertThat(response.content()).isEqualTo(content);
        assertThat(response.rating()).isEqualTo(rating);
        assertThat(response.reviewTime()).isEqualTo(reviewTime);
    }
}