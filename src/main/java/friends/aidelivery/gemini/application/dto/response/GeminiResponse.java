package friends.aidelivery.gemini.application.dto.response;

import friends.aidelivery.gemini.domain.Gemini;
import friends.aidelivery.gemini.domain.RequestType;

public record GeminiResponse(RequestType type, String requestText, String responseText) {

    public static GeminiResponse of(final Gemini gemini) {
        return new GeminiResponse(gemini.getType(), gemini.getRequest().getValue(),
            gemini.getResponse());
    }
}
