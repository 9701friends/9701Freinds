package friends.aidelivery.user.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.application.dto.request.UserCreateRequest;
import friends.aidelivery.user.application.dto.request.UserLoginRequest;
import friends.aidelivery.user.application.dto.response.UserCreateResponse;
import friends.aidelivery.user.application.dto.response.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestBody final UserCreateRequest userCreateRequest) {
        UserCreateResponse response = userService.singIn(userCreateRequest);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(
        @RequestBody final UserLoginRequest userLoginRequest){

        UserLoginResponse response = userService.login(userLoginRequest);
        log.info("리스폰스 이메일 : {}",response.email());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("{user_id}")
    public ResponseEntity<CommonResponse> findUser(@PathVariable Long user_id) {
        UserLoginResponse response = userService.findUser(user_id);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }
}
