package friends.aidelivery.user.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.application.dto.request.UserLoginRequestDto;
import friends.aidelivery.user.application.dto.response.UserInfoResponseDto;
import friends.aidelivery.user.application.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(
            @RequestBody final UserLoginRequestDto userLoginRequest) {

        UserResponseDto response = userService.login(userLoginRequest);
        log.info("리스폰스 이메일 : {}", response.email());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("{user_id}")
    public ResponseEntity<CommonResponse> findUser(@PathVariable Long user_id) {
        UserResponseDto response = userService.findUser(user_id);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<CommonResponse> updateUser(@PathVariable Long user_id, @RequestBody UserInfoRequestDto requestDto) {
        UserResponseDto response = userService.updateUserInfo(user_id, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable Long user_id, @RequestBody UserLoginRequestDto requestDto) {
        UserResponseDto response = userService.deleteUser(user_id, requestDto);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }
}
