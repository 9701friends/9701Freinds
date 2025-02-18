package friends.aidelivery.user.domain.repository;

import friends.aidelivery.user.domain.User;

public interface UserRepository {

    User save(User user);
}
