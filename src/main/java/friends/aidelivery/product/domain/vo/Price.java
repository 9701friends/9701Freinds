package friends.aidelivery.product.domain.vo;

import friends.aidelivery.product.exception.ProductPriceMaxException;
import friends.aidelivery.product.exception.ProductPriceMinException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Price {

    public static final long MIN = 0;
    public static final long MAX = 100000000;

    @Column(name = "price", nullable = false)
    private Long value;

    public Price(final Long value) {
        validate(value);
        this.value = value;
    }

    private void validate(final Long value) {
        if (value == null) {
            throw new NullPointerException("가격은 null일 수 없습니다.");
        }
        if (value < MIN) {
            throw new ProductPriceMinException(MIN, value);
        }
        if (value > MAX) {
            throw new ProductPriceMaxException(MAX, value);
        }
    }

    public Price update(final Long value) {
        return new Price(value);
    }
}
