package friends.aidelivery.user.application;

import friends.aidelivery.user.application.dto.request.UserCreateRequest;
import friends.aidelivery.user.application.dto.request.UserLoginRequest;
import friends.aidelivery.user.application.dto.response.UserCreateResponse;
import friends.aidelivery.user.application.dto.response.UserLoginResponse;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.domain.vo.Email;
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
    public UserCreateResponse singIn(UserCreateRequest userCreateRequest) {
        User user = User.createUser(userCreateRequest, passwordEncoder);
        User saved = userRepository.save(user);
        return UserCreateResponse.of(saved);
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest){

        Email email = new Email(userLoginRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(userLoginRequest.getPassword());

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!encodedPassword.equals(user.getPassword())) {
            throw new RuntimeException("Missmatch Password");
        }

        return UserLoginResponse.of(user);
    }

    public UserLoginResponse findUser(Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User Not Found"));

        return UserLoginResponse.of(user);
    }
}