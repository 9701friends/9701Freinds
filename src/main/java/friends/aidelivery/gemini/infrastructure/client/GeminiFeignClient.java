package friends.aidelivery.gemini.infrastructure.client;

import friends.aidelivery.gemini.application.dto.request.GeminiFeignRequest;
import friends.aidelivery.gemini.application.dto.response.GeminiFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geminiClient", url = "https://generativelanguage.googleapis.com/v1beta/models")
public interface GeminiFeignClient {

    @PostMapping("/gemini-1.5-flash:generateContent")
    GeminiFeignResponse generateContent(
        @RequestParam("key") String key,
        @RequestBody GeminiFeignRequest request
    );
}
