package friends.aidelivery.store.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Rating {

    private static final BigDecimal MAX_VALUE = new BigDecimal("5.0");
    private static final BigDecimal MIN_VALUE = new BigDecimal("1.0");

    @Column(name = "rating", nullable = true)
    private BigDecimal value;

    private void validate(BigDecimal value) {
        if (value == null) {
            throw new NullPointerException("필수 입력값입니다.");
        }
        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new IllegalArgumentException("범위를 벗어났습니다.");
        }
    }

    public Rating(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    public Rating update(BigDecimal value) {
        return new Rating(value);
    }
}
