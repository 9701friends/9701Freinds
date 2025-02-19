package friends.aidelivery.review.domain.vo;

import friends.aidelivery.review.exception.ReviewTimeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ReviewTime {

    public static final int REVIEW_PERIOD_DAYS = 3;

    @Column(name = "review_time", nullable = false)
    private LocalDateTime value;

    public ReviewTime(final LocalDateTime value, final LocalDateTime orderTime) {
        validate(value, orderTime);
        this.value = value;
    }

    private void validate(final LocalDateTime value, final LocalDateTime orderTime) {
        final LocalDateTime deadLine = orderTime.plusDays(REVIEW_PERIOD_DAYS);
        if (value.isAfter(deadLine)) {
            throw new ReviewTimeException(deadLine, value);
        }
    }

}
