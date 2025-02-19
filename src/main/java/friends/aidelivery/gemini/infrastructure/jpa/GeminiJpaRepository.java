package friends.aidelivery.gemini.infrastructure.jpa;

import friends.aidelivery.gemini.domain.Gemini;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeminiJpaRepository extends JpaRepository<Gemini, UUID> {

}
