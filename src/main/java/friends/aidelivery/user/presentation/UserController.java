package friends.aidelivery.user.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.request.UserLoginRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<CommonResponse> signIn(
        @RequestBody final UserInfoRequestDto userInfoRequestDto) {
        UserInfoResponseDto response = userService.singIn(userInfoRequestDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(
        @RequestBody final UserLoginRequestDto userLoginRequest) {

        UserResponseDto response = userService.login(userLoginRequest);
        log.info("리스폰스 이메일 : {}", response.email());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto response = userService.logout(userDetails);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponse> updateUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId,
        @RequestBody UserInfoRequestDto requestDto) {
        UserResponseDto response = userService.updateUserInfo(userDetails, userId, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long userId,
        @RequestBody UserLoginRequestDto requestDto) {
        UserResponseDto response = userService.deleteUser(userId, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse> findUserInfo(@PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto response = userService.findUserInfo(userId, userDetails);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }


}
