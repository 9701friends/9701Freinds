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
@Getter
@EqualsAndHashCode
public class Name {

    public static final int MAX_LENGTH = 20;

    @Column(name = "name", nullable = false, length = MAX_LENGTH)
    private String value;

    public Name(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (Objects.isNull(value)) {
            throw new UserBadRequestException(512, "이름은 필수 조건 항목입니다.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new UserBadRequestException(513, "이름은 20자를 초과할 수 없습니다.");
        }
    }
}
