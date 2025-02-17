package friends.aidelivery.gemini.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.gemini.application.GeminiService;
import friends.aidelivery.gemini.application.dto.request.GeminiCreateRequest;
import friends.aidelivery.gemini.application.dto.response.GeminiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("")
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody GeminiCreateRequest request) {
        GeminiResponse response = geminiService.create(request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }
}
