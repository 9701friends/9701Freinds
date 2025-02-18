package friends.aidelivery.user.application;

import friends.aidelivery.user.application.dto.request.UserCreateRequest;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void singIn(UserCreateRequest userCreateRequest) {
        User user = User.createUser(userCreateRequest, passwordEncoder);
        userRepository.save(user);
    }
}