package friends.aidelivery.gemini.application.dto.response;

import java.util.List;

public record GeminiFeignResponse(List<Candidate> candidates) {

    public String getFirstText() {
        return candidates().get(0).content().parts().get(0).text();
    }

    public record Candidate(Content content) {

    }

    public record Content(List<Part> parts) {

    }

    public record Part(String text) {

    }
}
