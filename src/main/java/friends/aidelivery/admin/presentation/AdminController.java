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

    @GetMapping("/{user_id}")
    public ResponseEntity<CommonResponse> findUser(@PathVariable Long user_id) {
        UserResponseDto response = adminService.findUser(user_id);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<CommonResponse> updateUser(@PathVariable Long user_id, @RequestBody AdminUserUpdateRequest requestDto) {
        AdminUserUpdateResponse response = adminService.updateUser(user_id, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long user_id) {
        adminService.deleteUser(user_id);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @PutMapping("/{user_id}/status")
    public ResponseEntity<CommonResponse> updateUserStatus(@PathVariable Long user_id, @RequestBody AdminUserStatusRequestDto requestDto) {
        AdminUserUpdateResponse response = adminService.updateUserStatus(user_id, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }
}
