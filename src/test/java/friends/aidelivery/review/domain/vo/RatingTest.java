package friends.aidelivery.review.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import friends.aidelivery.review.exception.ReviewRatingMaxException;
import friends.aidelivery.review.exception.ReviewRatingMinException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RatingTest {

    @DisplayName("평점 생성이 성공한다.")
    @Test
    void rating_success() {
        // given & when
        final Integer value = 4;
        final Rating rating = new Rating(value);
        // then
        assertThat(rating.getValue()).isEqualTo(value);
    }

    @DisplayName("최저 평점보다 낮으면 예외가 발생한다.")
    @Test
    void rating_min_fail() {
        // given
        final int min = Rating.MIN_RATING;
        final Integer value = min - 1;
        // when & then
        assertThatThrownBy(() -> new Rating(value))
            .isInstanceOf(ReviewRatingMinException.class)
            .hasMessageContaining(String.format(
                "평점이 최소 평점보다 낮습니다. - 요청 정보 { 최저 평점 : %d, 입력 값 : %d }",
                min,
                value));
    }

    @DisplayName("최대 평점보다 높으면 예외가 발생한다.")
    @Test
    void rating_max_fail() {
        // given
        final int max = Rating.MAX_RATING;
        final Integer value = max + 1;
        // when & then
        assertThatThrownBy(() -> new Rating(value))
            .isInstanceOf(ReviewRatingMaxException.class)
            .hasMessageContaining(String.format(
                "평점이 최대 평점보다 높습니다. - 요청 정보 { 최대 평점 : %d, 입력 값 : %d }",
                max,
                value));
    }
}