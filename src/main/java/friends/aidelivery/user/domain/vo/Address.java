package friends.aidelivery.user.domain.vo;

import friends.aidelivery.user.domain.exception.UserBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Address {

    @Column(name = "address", nullable = false)
    private String value;

    public Address(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (Objects.isNull(value)) {
            throw new UserBadRequestException(514, "주소는 필수 입력 조건입니다.");
        }
    }
}
