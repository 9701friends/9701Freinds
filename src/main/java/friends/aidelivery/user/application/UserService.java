package friends.aidelivery.user.application;

import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.user.application.dto.request.UserDeleteRequestDto;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.domain.vo.Password;
import friends.aidelivery.user.exception.UserDuplicateEmailException;
import friends.aidelivery.user.exception.UserDuplicatePhoneException;
import friends.aidelivery.user.exception.UserMismatchException;
import friends.aidelivery.user.exception.UserNotFoundException;
import friends.aidelivery.user.exception.UserPasswordMismatchException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            Password encodePassword = Password.of(userInfoRequestDto.password(),
                passwordEncoder);
            User user = User.createUser(userInfoRequestDto, encodePassword);
            User saved = userRepository.save(user);
            return UserInfoResponseDto.of(saved);
        } catch (DataIntegrityViolationException e) {
            handleDuplicateKeyException(e);
        }
        return null;
    }

    public UserInfoResponseDto findUserInfo(Long userId, UserDetailsImpl userDetails) {

        User user = getUserOrElseThrow(userId);

        userMismatch(userDetails, user.getId());

        return UserInfoResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto updateUserInfo(UserDetailsImpl userDetails, Long userId,
        UserInfoRequestDto userInfoRequestDto) {

        User user = getUserOrElseThrow(userId);

        userMismatch(userDetails, userId);
        Password encodePassword = Password.of(userInfoRequestDto.password(),
            passwordEncoder);
        user.updateUser(userInfoRequestDto, encodePassword);
        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto deleteUser(UserDetailsImpl userDetails, Long userId,
        UserDeleteRequestDto requestDto) {
        User user = getUserOrElseThrow(userId);
        userMismatch(userDetails, userId);
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword().getValue())) {
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

    private void handleDuplicateKeyException(DataIntegrityViolationException e) {
        String message = Objects.requireNonNull(e.getRootCause()).getMessage(); // DB 에러 메시지 추출
        if (message.contains("email")) {
            throw new UserDuplicateEmailException();
        } else if (message.contains("phone")) {
            throw new UserDuplicatePhoneException();
        }
    }
}