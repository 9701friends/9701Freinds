package friends.aidelivery.admin.presentation;

import friends.aidelivery.admin.application.AdminService;
import friends.aidelivery.admin.application.dto.request.AdminUserStatusRequestDto;
import friends.aidelivery.admin.application.dto.request.AdminUserUpdateRequest;
import friends.aidelivery.admin.application.dto.response.AdminUserRequestDto;
import friends.aidelivery.admin.application.dto.response.AdminUserUpdateResponse;
import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import friends.aidelivery.user.domain.enums.UserRoleEnum.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Secured({Authority.MANAGER, Authority.MASTER})
    @GetMapping("/users")
    public ResponseEntity<CommonResponse> getUserList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc) {

        Page<AdminUserRequestDto> response = adminService.getUserList(page, size, sortBy, isAsc);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({Authority.MANAGER, Authority.MASTER})
    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse> findUser(@PathVariable Long userId) {
        UserResponseDto response = adminService.findUser(userId);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({Authority.MANAGER, Authority.MASTER})
    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponse> updateUser(@PathVariable Long userId,
        @RequestBody AdminUserUpdateRequest requestDto) {
        AdminUserUpdateResponse response = adminService.updateUser(userId, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({Authority.MANAGER, Authority.MASTER})
    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @Secured({Authority.MANAGER, Authority.MASTER})
    @PutMapping("/status/{userId}")
    public ResponseEntity<CommonResponse> updateUserStatus(@PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody AdminUserStatusRequestDto requestDto) {
        AdminUserUpdateResponse response = adminService.updateUserStatus(userDetails, userId,
            requestDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }
}
