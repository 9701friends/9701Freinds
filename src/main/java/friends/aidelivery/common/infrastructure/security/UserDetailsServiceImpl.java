package friends.aidelivery.common.infrastructure.security;

import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 시도:{}", username);

        Email email = new Email(username.toLowerCase());

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.error("이메일 '{}'에 해당하는 유저를 찾을 수 없음!", email.getValue());
                return new UsernameNotFoundException(
                    "이메일 " + email.getValue() + "에 해당하는 유저를 찾을 수 없습니다.");
            });

        log.info("로그인:{}", user);
        return new UserDetailsImpl(user.getEmail().getValue(), user.getId(), user.getRole(),
            user.getPassword());
    }
}
