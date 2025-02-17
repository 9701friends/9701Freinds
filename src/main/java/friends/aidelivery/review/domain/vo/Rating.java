package friends.aidelivery.review.domain.vo;

import friends.aidelivery.review.exception.ReviewRatingMaxException;
import friends.aidelivery.review.exception.ReviewRatingMinException;
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
public class Rating {

    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 5;

    @Column(name = "rating", nullable = false)
    private Integer value;

    public Rating(final Integer value) {
        validate(value);
        this.value = value;
    }

    private void validate(final Integer value) {
        if (value == null) {
            throw new NullPointerException("평점은 필수입니다.");
        }
        if (value < MIN_RATING) {
            throw new ReviewRatingMinException(MIN_RATING, value);
        }
        if (value > MAX_RATING) {
            throw new ReviewRatingMaxException(MAX_RATING, value);
        }
    }
}
