package friends.aidelivery.user.domain.vo;

import friends.aidelivery.user.domain.exception.UserBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Nickname {

    public static final int MAX_LENGTH = 20;

    @Column(name = "nickname", nullable = false, length = MAX_LENGTH)
    private String value;

    public Nickname(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (Objects.isNull(value) || value.trim().isEmpty()) {
            this.value = generateNickname();
        } else if (value.length() > MAX_LENGTH) {
            throw new UserBadRequestException(517, "닉네임은 20자를 초과할 수 없습니다.");
        }
    }

    private static final List<String> WORDS = List.of("꿩", "닭", "오리", "참새", "비둘기", "펭귄");
    private static final List<String> PARTICLES = List.of("대신", "보다", "좋은", "싫은");

    private static final Random RANDOM = new Random();

    public static String generateNickname() {
        String first = WORDS.get(ThreadLocalRandom.current().nextInt(WORDS.size()));
        String second = WORDS.get(ThreadLocalRandom.current().nextInt(WORDS.size()));
        String particle = PARTICLES.get(
            ThreadLocalRandom.current().nextInt(PARTICLES.size())); // 랜덤 조사 선택

        // 같은 단어가 반복되지 않도록 체크
        while (first.equals(second)) {
            second = WORDS.get(ThreadLocalRandom.current().nextInt(WORDS.size()));
        }

        // 100~999 랜덤 숫자 추가
        int randomNumber = RANDOM.nextInt(900) + 100;

        return first + particle + second + randomNumber;
    }
}
