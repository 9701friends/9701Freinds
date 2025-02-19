package friends.aidelivery.user.application;

import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.request.UserLoginRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
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
    public UserInfoResponseDto singIn(UserInfoRequestDto userInfoRequestDto) {
        User user = User.createUser(userInfoRequestDto, passwordEncoder);
        User saved = userRepository.save(user);
        return UserInfoResponseDto.of(saved);
    }

    public UserResponseDto login(UserLoginRequestDto userLoginRequest) {

        Email email = new Email(userLoginRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(userLoginRequest.getPassword());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!encodedPassword.equals(user.getPassword())) {
            throw new RuntimeException("Missmatch Password");
        }

        return UserResponseDto.of(user);
    }

    public UserResponseDto findUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto updateUserInfo(Long userId, UserInfoRequestDto userInfoRequestDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.updateUser(userInfoRequestDto);
        return UserResponseDto.of(user);
    }
}