package friends.aidelivery.product.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import friends.aidelivery.product.exception.ProductContentBlankException;
import friends.aidelivery.product.exception.ProductContentLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ContentTest {

    @DisplayName("내용 생성이 성공한다.")
    @Test
    void content_success() {
        // given & when
        String value = "테스트내용";
        Content content = new Content(value);
        // then
        assertNotNull(content);
        assertEquals(value, content.getValue());
    }

    @DisplayName("Null 입력시 예외가 발생한다.")
    @Test
    void content_null_fail() {
        // given
        String value = null;
        // when & then
        assertThatThrownBy(() -> new Content(value))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("내용은 null일 수 없습니다.");
    }

    @DisplayName("공백 입력시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     "})
    void content_blank_fail(final String value) {
        assertThatThrownBy(() -> new Content(value))
            .isInstanceOf(ProductContentBlankException.class)
            .hasMessageContaining("내용은 공백을 제외한 1자 이상이어야 합니다.");
    }

    @DisplayName("최대 길이 초과시 예외가 발생한다.")
    @Test
    void content_max_length_fail() {
        // given
        final int maxLength = Content.MAX_LENGTH;
        final String value = "가".repeat(maxLength + 1);
        // when & then
        assertThatThrownBy(() -> new Content(value))
            .isInstanceOf(ProductContentLengthException.class)
            .hasMessageContaining(String.format(
                "내용의 길이가 최대 길이를 초과했습니다. - 요청 정보 { 허용 값 : %d, 입력 값 : %d }",
                maxLength,
                value.length()));
    }

    @DisplayName("내용 업데이트가 성공한다.")
    @Test
    void content_update_success() {
        // given
        String value = "기본 내용";
        Content content = new Content(value);
        String updatedValue = "업데이트된 내용";
        // when
        Content updatedContent = content.update(updatedValue);
        // then
        assertEquals(updatedValue, updatedContent.getValue());
    }
}