package friends.aidelivery.user.domain.vo;

import friends.aidelivery.user.exception.UserBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Phone {

    private static final String PHONE_REGEX = "^010\\d{4}\\d{4}$";

    @Column(name = "phone", nullable = false, unique = true)
    private String value;

    public Phone(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (Objects.isNull(value)) {
            throw new UserBadRequestException(515, "휴대 전화 번호는 필수 입력 조건입니다.");
        }

        if (isNotPhoneFormat(value)) {
            throw new UserBadRequestException(516, "제대로 된 휴대 전화 번호가 아닙니다.");
        }
    }

    private boolean isNotPhoneFormat(final String value) {
        return !Pattern.matches(PHONE_REGEX, value);
    }
}
