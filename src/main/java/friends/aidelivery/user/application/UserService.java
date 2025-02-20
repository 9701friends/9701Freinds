package friends.aidelivery.user.application;

import friends.aidelivery.admin.application.dto.response.AdminUserRequestDto;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.request.UserLoginRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.domain.vo.Email;
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

    public UserResponseDto findUserInfo(Long userId, UserDetailsImpl userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!userDetails.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User Not Match");
        }

        return UserResponseDto.of(user);
    }

    @Transactional
    public UserResponseDto updateUserInfo(UserDetailsImpl userDetails, Long userId, UserInfoRequestDto userInfoRequestDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        if(!userDetails.getUser().getId().equals(userId)) {
            throw new RuntimeException("User Not Match");
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
        return UserResponseDto.of(userDetails.getUser());
    }

    public Page<AdminUserRequestDto> findAllUser(UserDetailsImpl userDetails) {
        if (!userDetails.getUser().getRole().equals(UserRoleEnum.MASTER)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Sort.Direction direction = Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(0, 10, Sort.by(direction, "nickname"));

        Page< User> userPage = userRepository.findAll(pageable);

        return userPage.map(AdminUserRequestDto::of);
    }
}