package friends.aidelivery.user.infrastructure.repository;

import static friends.aidelivery.user.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.domain.vo.Email;
import friends.aidelivery.user.infrastructure.repository.jpa.UserJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User save(User user) {
        return jpaRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(Email email) {

        User findUser = jpaQueryFactory
            .selectFrom(user)
            .where(user.email.value.eq(email.getValue().toLowerCase()))
            .fetchFirst();

        if (findUser == null) {
            log.warn("유저를 찾지 못함: {}", email.getValue());
            return Optional.empty();
        }

        log.info("조회된 유저: {}", findUser);
        return Optional.of(findUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id);
    }
}
