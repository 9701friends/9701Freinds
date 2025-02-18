package friends.aidelivery.user.infrastructure.repository;

import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.infrastructure.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {
        return jpaRepository.save(user);
    }
}
