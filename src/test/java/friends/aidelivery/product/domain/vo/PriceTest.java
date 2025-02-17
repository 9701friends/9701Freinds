package friends.aidelivery.product.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import friends.aidelivery.product.exception.ProductPriceMaxException;
import friends.aidelivery.product.exception.ProductPriceMinException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

    @DisplayName("가격 생성이 성공한다.")
    @Test
    void price_success() {
        // given & when
        Long value = 1000L;
        Price price = new Price(value);
        // then
        assertNotNull(price);
        assertEquals(value, price.getValue());
    }

    @DisplayName("Null 입력시 예외가 발생한다.")
    @Test
    void price_null_fail() {
        // given
        Long value = null;
        // when & then
        assertThatThrownBy(() -> new Price(value))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("가격은 null일 수 없습니다.");
    }

    @DisplayName("최소 가격 미만일 경우 예외가 발생한다.")
    @Test
    void price_min_fail() {
        // given
        final long min = Price.MIN;
        final Long value = min - 1;
        // when & then
        assertThatThrownBy(() -> new Price(value))
            .isInstanceOf(ProductPriceMinException.class)
            .hasMessageContaining(String.format(
                "가격은 최저 가격보다 높아야 합니다. - 요청 정보 { 최저 가격 : %d, 입력 값 : %d }",
                min,
                value));
    }

    @DisplayName("최대 가격 초과일 경우 예외가 발생한다.")
    @Test
    void price_max_fail() {
        // given
        final long max = Price.MAX;
        final Long value = max + 1; // 최대값보다 큰 값
        // when & then
        assertThatThrownBy(() -> new Price(value))
            .isInstanceOf(ProductPriceMaxException.class)
            .hasMessageContaining(String.format(
                "가격은 최대 가격보다 낮아야 합니다. - 요청 정보 { 최대 가격 : %d, 입력 값 : %d }",
                max,
                value));
    }

    @DisplayName("가격 업데이트가 성공한다.")
    @Test
    void price_update_success() {
        // given
        Long value = 5000L;
        Price price = new Price(value);
        Long updatedValue = 2000L;
        // when
        Price updatedPrice = price.update(updatedValue);
        // then
        assertEquals(updatedValue, updatedPrice.getValue());
    }
}
