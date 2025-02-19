package friends.aidelivery.user.domain.vo;

import friends.aidelivery.user.domain.exception.UserBadRequestException;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@EqualsAndHashCode
public class Password  {

    private String value;

    // 기본 생성자 추가
    private Password(String value) {
        this.value = value;
    }

    // 정적 팩토리 메서드로 PasswordEncoder를 이용한 암호화 처리
    public static Password encrypt(final String rawPassword, PasswordEncoder passwordEncoder) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new UserBadRequestException(518, "비밀번호는 필수 조건 항목입니다.");
        }
        return new Password(passwordEncoder.encode(rawPassword));
    }
}