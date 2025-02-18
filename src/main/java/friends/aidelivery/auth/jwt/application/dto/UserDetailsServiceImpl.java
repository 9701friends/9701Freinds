package friends.aidelivery.auth.jwt.application.dto;

import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.exception.UserBadRequestException;
import friends.aidelivery.user.domain.repository.UserRepository;
import friends.aidelivery.user.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Email email = new Email(username);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserBadRequestException(519, "유저를 찾을 수 없습니다."));
        return new UserDetailsImpl(user);
    }
}
