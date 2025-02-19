package friends.aidelivery.review.domain.vo;

import friends.aidelivery.review.exception.ReviewContentLengthException;
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
public class ReviewContent {

    public static final int MAX_LENGTH = 100;
    @Column(name = "content", length = MAX_LENGTH)
    private String value;

    public ReviewContent(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new ReviewContentLengthException(MAX_LENGTH, value);
        }
    }

    public ReviewContent update(final String value) {
        return new ReviewContent(value);
    }
}
