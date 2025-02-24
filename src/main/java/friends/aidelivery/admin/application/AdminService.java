package friends.aidelivery.admin.application;

import friends.aidelivery.admin.application.dto.request.AdminUserStatusRequestDto;
import friends.aidelivery.admin.application.dto.request.AdminUserUpdateRequest;
import friends.aidelivery.admin.application.dto.response.AdminUserRequestDto;
import friends.aidelivery.admin.application.dto.response.AdminUserUpdateResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.exception.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;

    public Page<AdminUserRequestDto> getUserList(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<User> userPage = userRepository.findAllByIsDeletedFalse(pageable);

        return userPage.map(AdminUserRequestDto::of);
    }

    public UserInfoResponseDto findUser(Long userId) {

        return UserInfoResponseDto.of(userService.getUserOrElseThrow(userId));
    }

    @Transactional
    public AdminUserUpdateResponse updateUser(Long userId, AdminUserUpdateRequest requestDto) {
        User user = userService.getUserOrElseThrow(userId);
        user.updateUserByAdmin(requestDto);

        return AdminUserUpdateResponse.of(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userService.getUserOrElseThrow(userId);
        user.softDeleteUser();
    }

    @Transactional
    public AdminUserUpdateResponse updateUserStatus(UserDetailsImpl userDetails, Long userId,
        AdminUserStatusRequestDto requestDto) {

        // MANAGER 권한은 MASTER로 변경 불가
        if (userDetails.getRole().equals(UserRoleEnum.MANAGER) && requestDto.role()
            .equals(UserRoleEnum.MASTER)) {
            throw new UserUnauthorizedException();
        }

        User user = userService.getUserOrElseThrow(userId);

        user.updateUserRole(requestDto.role());
        return AdminUserUpdateResponse.of(user);
    }
}
