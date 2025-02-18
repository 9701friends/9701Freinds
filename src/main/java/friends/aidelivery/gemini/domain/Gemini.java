package friends.aidelivery.gemini.domain;

import friends.aidelivery.gemini.domain.vo.GeminiRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_gemini")
@Entity
public class Gemini {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "gemini_id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RequestType type;

    @Embedded
    private GeminiRequest request;

    @Column(name = "response", nullable = false)
    private String response;

    public Gemini(final RequestType type, final GeminiRequest request, final String response) {
        this.type = type;
        this.request = request;
        this.response = response;
    }
}
