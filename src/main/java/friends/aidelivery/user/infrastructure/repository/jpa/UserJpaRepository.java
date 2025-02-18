package friends.aidelivery.user.infrastructure.repository.jpa;

import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.vo.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(Email email);
}
