package friends.aidelivery.user.domain.repository;

import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.vo.Email;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(Email email);

    Optional<User> findById(Long id);
}
