package friends.aidelivery.product.domain.vo;

import friends.aidelivery.product.exception.ProductNameBlankException;
import friends.aidelivery.product.exception.ProductNameLengthException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Name {

    public static final int MAX_LENGTH = 20;

    @Column(name = "name", nullable = false, length = MAX_LENGTH)
    private String value;

    public Name(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null) {
            throw new NullPointerException("이름은 null일 수 없습니다.");
        }
        if (value.isBlank()) {
            throw new ProductNameBlankException();
        }
        if (value.length() > MAX_LENGTH) {
            throw new ProductNameLengthException(MAX_LENGTH, value);
        }
    }

    public Name update(final String value) {
        return new Name(value);
    }
}

