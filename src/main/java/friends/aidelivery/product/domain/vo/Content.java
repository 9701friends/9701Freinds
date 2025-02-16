package friends.aidelivery.product.domain.vo;

import friends.aidelivery.product.exception.ProductContentBlankException;
import friends.aidelivery.product.exception.ProductContentLengthException;
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
public class Content {

    public static final int MAX_LENGTH = 50;
    @Column(name = "content", nullable = false, length = MAX_LENGTH)
    private String value;

    public Content(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null) {
            throw new NullPointerException("내용은 null일 수 없습니다.");
        }
        if (value.isBlank()) {
            throw new ProductContentBlankException();
        }
        if (value.length() > MAX_LENGTH) {
            throw new ProductContentLengthException(MAX_LENGTH, value);
        }
    }

    public Content update(final String value) {
        return new Content(value);
    }
}
