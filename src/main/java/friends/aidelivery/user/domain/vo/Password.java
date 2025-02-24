package friends.aidelivery.user.domain.vo;

import friends.aidelivery.user.exception.UserBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Password {

    /**
     * 최소 8자 이상이며, 최소 하나의 숫자, 하나의 특수문자를 포함해야 함
     */
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>_]).{8,}$";
    
    @Column(name = "password", nullable = false)
    private String value;

    // 암호화된 비밀번호를 받는 생성자
    private Password(final String encryptedValue) {
        this.value = encryptedValue;
    }

    /**
     * 비밀번호 검증 및 암호화 후 객체 생성
     */
    public static Password of(final String rawPassword, PasswordEncoder passwordEncoder) {
        validate(rawPassword);
        return new Password(passwordEncoder.encode(rawPassword));
    }

    /**
     * 비밀번호 유효성 검사
     */
    private static void validate(final String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new UserBadRequestException(518, "비밀번호는 필수 조건 항목입니다.");
        }

        if (!Pattern.matches(PASSWORD_REGEX, rawPassword)) {
            throw new UserBadRequestException(519, "비밀번호는 최소 8자 이상이며, 최소 하나의 숫자 및 특수문자를 포함해야 합니다.");
        }
    }
}