package friends.aidelivery.review.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import friends.aidelivery.review.exception.ReviewTimeException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewTimeTest {

    @DisplayName("리뷰 작성기한을 만족하면 성공한다.")
    @Test
    void reviewTime_success() {
        // given & when
        final int periodDays = ReviewTime.REVIEW_PERIOD_DAYS;
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime orderTime = now.minusDays(periodDays);
        final ReviewTime reviewTime = new ReviewTime(now, orderTime);
        // then
        assertThat(reviewTime.getValue()).isEqualTo(now);
    }

    @DisplayName("리뷰 작성기한이 지나면 실패한다.")
    @Test
    void reviewTime_fail() {
        // given
        final int periodDays = ReviewTime.REVIEW_PERIOD_DAYS;
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime orderTime = now.minusDays(periodDays + 1);
        // when & then
        assertThatThrownBy(() -> new ReviewTime(now, orderTime))
            .isInstanceOf(ReviewTimeException.class)
            .hasMessageContaining("리뷰를 작성할 수 있는 기간이 지났습니다.");

    }
}