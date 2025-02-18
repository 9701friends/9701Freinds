package friends.aidelivery.user.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.user.application.UserService;
import friends.aidelivery.user.application.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<CommonResponse> signIn(
        @RequestBody final UserCreateRequest userCreateRequest) {
        return userService.singIn(userCreateRequest);
    }
}
