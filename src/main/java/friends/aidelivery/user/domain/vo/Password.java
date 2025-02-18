package friends.aidelivery.user.domain.vo;

import friends.aidelivery.user.domain.exception.UserBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@Getter
@EqualsAndHashCode
public class Password {

    @Column(name = "password", nullable = false)
    private String value;

    // 기본 생성자 추가
    protected Password() {
        this.value = null; // 기본 생성자에서 초기화하지 않음
    }

    // 비밀번호를 암호화하여 저장하는 생성자
    public Password(final String value, PasswordEncoder passwordEncoder) {
        this.value = encryptPassword(value, passwordEncoder);
    }

    // 비밀번호 암호화 로직
    private String encryptPassword(final String value, PasswordEncoder passwordEncoder) {
        if (value == null || value.isEmpty()) {
            throw new UserBadRequestException(518, "비밀번호는 필수 조건 항목입니다.");
        }
        return passwordEncoder.encode(value);  // 비밀번호 암호화
    }
}