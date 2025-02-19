package friends.aidelivery.gemini.infrastructure.repository;

import friends.aidelivery.gemini.domain.Gemini;
import friends.aidelivery.gemini.domain.repository.GeminiRepository;
import friends.aidelivery.gemini.infrastructure.jpa.GeminiJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GeminiRepositoryImpl implements GeminiRepository {

    private final GeminiJpaRepository geminiJpaRepository;

    @Override
    public Gemini save(Gemini gemini) {
        return geminiJpaRepository.save(gemini);
    }
}
