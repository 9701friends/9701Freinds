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

@EqualsAndHashCode//(onlyExplicitlyIncluded = true)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Email {

    /**
     * 사용자명은 알파벳, 숫자, 밑줄, 대시, 마침표로 구성되어 있고, 숫자 또는 알파벳으로 끝나야 함 도메인은 알파벳 또는 숫자, 마침표로 구성됨 도메인 확장자는 최소 두
     * 자리에서 최대 여덟 자리까지 가능
     */
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,8}$";

    //@EqualsAndHashCode.Include
    @Column(name = "email", nullable = false, unique = true)
    private String value;

    public Email(final String value) {
        validate(value);
        this.value = value.toLowerCase();
    }

    private void validate(final String value) {
        if (Objects.isNull(value)) {
            throw new UserBadRequestException(510, "이메일은 필수 입력 조건입니다.");
        }

        if (isNotEmailFormat(value)) {
            throw new UserBadRequestException(511, "제대로 된 이메일이 아닙니다.");
        }
    }

    private boolean isNotEmailFormat(final String value) {
        return !Pattern.matches(EMAIL_REGEX, value);
    }
}
