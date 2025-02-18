package friends.aidelivery.user.application;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.user.application.dto.request.UserCreateRequest;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<CommonResponse> singIn(UserCreateRequest userCreateRequest) {
        User user = User.createUser(userCreateRequest, passwordEncoder);
        userRepository.save(user);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(user.getId()),
            HttpStatus.OK);
    }
}