package friends.aidelivery.user.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.application.dto.request.UserDeleteRequestDto;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<CommonResponse> signIn(
        @RequestBody @Valid final UserInfoRequestDto userInfoRequestDto) {
        UserInfoResponseDto response = userService.singIn(userInfoRequestDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto response = userService.logout();
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponse> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId,
        @RequestBody @Valid UserInfoRequestDto requestDto) {
        UserResponseDto response = userService.updateUserInfo(userDetails, userId, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid UserDeleteRequestDto requestDto) {
        UserResponseDto response = userService.deleteUser(userDetails, userId, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse> findUserInfo(@PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserResponseDto response = userService.findUserInfo(userId, userDetails);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

}
