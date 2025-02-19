package friends.aidelivery.gemini.application;

import friends.aidelivery.gemini.application.dto.request.GeminiCreateRequest;
import friends.aidelivery.gemini.application.dto.request.GeminiFeignRequest;
import friends.aidelivery.gemini.application.dto.response.GeminiFeignResponse;
import friends.aidelivery.gemini.application.dto.response.GeminiResponse;
import friends.aidelivery.gemini.domain.Gemini;
import friends.aidelivery.gemini.domain.repository.GeminiRepository;
import friends.aidelivery.gemini.domain.vo.GeminiRequest;
import friends.aidelivery.gemini.infrastructure.client.GeminiFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GeminiService {

    private final GeminiFeignClient geminiFeignClient;
    private final GeminiRepository geminiRepository;

    @Value("${google.api.key}")
    private String key;

    @Transactional
    public GeminiResponse create(final GeminiCreateRequest request) {
        final GeminiRequest geminiRequest = new GeminiRequest(request.content());

        final String response = generateContent(geminiRequest);

        final Gemini gemini = new Gemini(request.requestType(), geminiRequest, response);
        final Gemini saved = geminiRepository.save(gemini);
        return GeminiResponse.of(saved);
    }

    private String generateContent(GeminiRequest geminiRequest) {
        final GeminiFeignRequest feignRequest = GeminiFeignRequest.of(geminiRequest.getValue());
        final GeminiFeignResponse response = geminiFeignClient.generateContent(
            key, feignRequest);
        return response.getFirstText();
    }
}
