package friends.aidelivery.gemini.application.dto.request;

import java.util.List;

public record GeminiFeignRequest(List<Content> contents) {

    public static GeminiFeignRequest of(final String text) {
        final Part part = new Part(text);
        final Content content = new Content(List.of(part));
        return new GeminiFeignRequest(List.of(content));
    }

    public record Content(List<Part> parts) {

    }

    public record Part(String text) {

    }
}
