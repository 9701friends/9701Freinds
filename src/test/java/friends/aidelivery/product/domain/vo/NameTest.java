package friends.aidelivery.product.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import friends.aidelivery.product.exception.ProductNameBlankException;
import friends.aidelivery.product.exception.ProductNameLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @DisplayName("이름 생성이 성공한다.")
    @Test
    void name_success() {
        // given & when
        String value = "테스트이름";
        Name name = new Name(value);
        // then
        assertNotNull(name);
        assertEquals(value, name.getValue());
    }

    @DisplayName("Null 입력시 예외가 발생한다.")
    @Test
    void name_null_fail() {
        // given
        String value = null;
        // when & then
        assertThatThrownBy(() -> new Name(value))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("이름은 null일 수 없습니다.");
    }

    @DisplayName("공백 입력시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     "})
    void name_blank_fail(final String value) {
        // when & then
        assertThatThrownBy(() -> new Name(value))
            .isInstanceOf(ProductNameBlankException.class)
            .hasMessageContaining("이름은 공백을 제외한 1자 이상이어야 합니다.");
    }

    @DisplayName("최대 길이 초과시 예외가 발생한다.")
    @Test
    void name_max_length_fail() {
        // given
        final int maxLength = Name.MAX_LENGTH;
        final String value = "가".repeat(maxLength + 1);
        // when & then
        assertThatThrownBy(() -> new Name(value))
            .isInstanceOf(ProductNameLengthException.class)
            .hasMessageContaining(String.format(
                "이름의 길이가 최대 길이를 초과했습니다. - 요청 정보 { 허용 값 : %d, 입력 값 : %d }",
                maxLength,
                value.length()));
    }

    @DisplayName("이름 업데이트가 성공한다.")
    @Test
    void name_update_success() {
        // given
        String value = "기본";
        Name name = new Name(value);
        String updatedValue = "업데이트";
        // when
        Name updatedName = name.update(updatedValue);
        // then
        assertEquals(updatedValue, updatedName.getValue());
    }
}