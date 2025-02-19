package friends.aidelivery.review.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import friends.aidelivery.review.exception.ReviewContentLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReviewContentTest {

    @DisplayName("리뷰 내용 생성이 성공한다.")
    @Test
    void reviewContent_success() {
        // given & when
        final String value = "리뷰 내용입니다.";
        final ReviewContent content = new ReviewContent(value);
        // then
        assertThat(content).isEqualTo(new ReviewContent(value));
    }

    @DisplayName("리뷰 내용의 최대 길이 초과시 예외가 발생한다.")
    @Test
    void reviewContent_max_fail() {
        // given
        final int max = ReviewContent.MAX_LENGTH;
        final String value = "가".repeat(max + 1);
        // when & then
        assertThatThrownBy(() -> new ReviewContent(value))
            .isInstanceOf(ReviewContentLengthException.class)
            .hasMessageContaining("내용의 길이가 최대 길이를 초과했습니다.");
    }
}