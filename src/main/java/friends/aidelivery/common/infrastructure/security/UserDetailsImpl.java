package friends.aidelivery.common.infrastructure.security;

import friends.aidelivery.user.domain.enums.UserRoleEnum;
import friends.aidelivery.user.domain.vo.Email;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j(topic = "UserDetailsImpl")
@Getter
public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final Long userId;
    private final UserRoleEnum role;  // Role 정보도 저장
    private final String password;
    private final List<GrantedAuthority> authorities;

    public Email getEmail() {
        return new Email(email);
    }

    public UserDetailsImpl(String email, Long userId, UserRoleEnum role, String password) {
        this.email = email;
        this.userId = userId;
        this.role = role;
        this.password = password;
        this.authorities = AuthorityUtils.createAuthorityList(role.name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}