package friends.aidelivery.admin.presentation;

import friends.aidelivery.admin.application.AdminService;
import friends.aidelivery.admin.application.dto.request.AdminUserStatusRequestDto;
import friends.aidelivery.admin.application.dto.request.AdminUserUpdateRequest;
import friends.aidelivery.admin.application.dto.response.AdminUserRequestDto;
import friends.aidelivery.admin.application.dto.response.AdminUserUpdateResponse;
import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<CommonResponse> getUserList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc) {

        Page<AdminUserRequestDto> response = adminService.getUserList(page, size, sortBy, isAsc);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse> findUser(@PathVariable Long userId) {
        UserResponseDto response = adminService.findUser(userId);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponse> updateUser(@PathVariable Long userId,
        @RequestBody AdminUserUpdateRequest requestDto) {
        AdminUserUpdateResponse response = adminService.updateUser(userId, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<CommonResponse> updateUserStatus(@PathVariable Long userId,
        @RequestBody AdminUserStatusRequestDto requestDto) {
        AdminUserUpdateResponse response = adminService.updateUserStatus(userId, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }
}
