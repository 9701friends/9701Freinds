package friends.aidelivery.user.application;

import friends.aidelivery.admin.application.dto.response.AdminUserRequestDto;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.request.UserLoginRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.exception.UserMismatchException;
import friends.aidelivery.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        if (!userDetails.getUserId().equals(user.getId())) {
            throw new UserMismatchException();
        }

        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto updateUserInfo(UserDetailsImpl userDetails, Long userId,
        UserInfoRequestDto userInfoRequestDto) {

        User user = getUserOrElseThrow(userId);

        if (!userDetails.getUserId().equals(userId)) {
            throw new UserMismatchException();
        }
        user.updateUser(userInfoRequestDto);
        return UserResponseDto.of(user);
    }

    public UserResponseDto deleteUser(Long userId, UserLoginRequestDto requestDto) {
        //todo 유저 숨김 처리
        return null;
    }

    public UserResponseDto logout(UserDetailsImpl userDetails) {
        /**
         * 프론트에서 토큰을 지워주는거라 딱히 여기서는 뭐 안함
         */
        //return UserResponseDto.of(userDetails.getUser());
        return null;
    }

    public Page<AdminUserRequestDto> findAllUser(UserDetailsImpl userDetails) {
       /*
        if (!userDetails.getUser().getRole().equals(UserRoleEnum.MASTER)) {
            throw new UserUnauthorizedException();
        }
        */
        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(0, 10, Sort.by(direction, "nickname"));

        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(AdminUserRequestDto::of);
    }

    public User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
    }
}