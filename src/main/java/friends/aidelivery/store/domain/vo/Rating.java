package friends.aidelivery.store.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Rating {

    private static final int MAX_VALUE = 5;
    private static final int MIN_VALUE = 1;

    @Column(name = "rating", nullable = true)
    private Long value;

    private void validate(Long value) {
        if (value == null) {
            throw new NullPointerException("필수 입력값입니다.");
        }
        if (value < MIN_VALUE || MAX_VALUE > 5) {
            throw new IllegalArgumentException("범위를 벗어났습니다.");
        }
    }

    public Rating(Long value) {
        validate(value);
        this.value = value;
    }

    public Rating update(Long value) {
        return new Rating(value);
    }
}
