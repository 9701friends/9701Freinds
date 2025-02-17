package friends.aidelivery.gemini.domain.vo;

import friends.aidelivery.gemini.exception.GeminiRequestBlankException;
import friends.aidelivery.gemini.exception.GeminiRequestLengthException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class GeminiRequest {

    public static final int MAX_LENGTH = 200;
    public static final String APPENDED_TEXT = "답변을 최대한 간결하게 50자 이하로";

    @Column(name = "request", nullable = false)
    private String value;

    public GeminiRequest(final String value) {
        validate(value);
        this.value = appendInstruction(value);
    }

    private void validate(final String value) {
        if (value == null) {
            throw new NullPointerException("요청 내용은 null일 수 없습니다.");
        }
        if (value.isBlank()) {
            throw new GeminiRequestBlankException();
        }
        if (value.length() > MAX_LENGTH) {
            throw new GeminiRequestLengthException(MAX_LENGTH, value);
        }
    }

    private String appendInstruction(final String value) {
        return value + " " + APPENDED_TEXT;
    }
}
