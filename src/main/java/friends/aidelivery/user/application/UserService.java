package friends.aidelivery.user.application;

import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.user.application.dto.request.UserDeleteRequestDto;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.exception.UserMismatchException;
import friends.aidelivery.user.exception.UserNotFoundException;
import friends.aidelivery.user.exception.UserPasswordMismatchException;
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

    public UserResponseDto findUserInfo(Long userId, UserDetailsImpl userDetails) {

        User user = getUserOrElseThrow(userId);

        userMismatch(userDetails, user.getId());

        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto updateUserInfo(UserDetailsImpl userDetails, Long userId,
        UserInfoRequestDto userInfoRequestDto) {

        User user = getUserOrElseThrow(userId);

        userMismatch(userDetails, userId);
        user.updateUser(userInfoRequestDto, passwordEncoder);
        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto deleteUser(UserDetailsImpl userDetails, Long userId,
        UserDeleteRequestDto requestDto) {
        User user = getUserOrElseThrow(userId);
        userMismatch(userDetails, userId);
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UserPasswordMismatchException();
        }
        user.softDeleteUser();
        return UserResponseDto.of(user);
    }

    public UserResponseDto logout() {

        return null;
    }

    public User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
    }

    private void userMismatch(UserDetailsImpl userDetails, Long userId) {
        if (!userDetails.getUserId().equals(userId)) {
            throw new UserMismatchException();
        }
    }
}