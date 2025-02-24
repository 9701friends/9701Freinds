package friends.aidelivery.user.domain.vo;

import friends.aidelivery.user.exception.UserBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

    @Column(name = "password", nullable = false)
    private String value;

    // 기본 생성자 추가
    private Password(final String value) {
        this.value = value;
    }

    // 암호화 처리
    public static Password encrypt(final String inputPassword, PasswordEncoder passwordEncoder) {
        if (inputPassword == null || inputPassword.isEmpty()) {
            throw new UserBadRequestException(518, "비밀번호는 필수 조건 항목입니다.");
        }
        return new Password(passwordEncoder.encode(inputPassword));
    }
}