package friends.aidelivery.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import friends.aidelivery.auth.jwt.JwtTokenProvider;
import friends.aidelivery.auth.jwt.exception.JwtTokenException;
import friends.aidelivery.user.application.dto.request.UserLoginRequest;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Slf4j(topic = "로그인 및 JWT 생성")
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException {
        try {

            UserLoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(),
                UserLoginRequest.class);

            log.info(loginRequest.toString());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword(), null);

            Authentication authentication = getAuthenticationManager().authenticate(
                authenticationToken);

            if (authentication.isAuthenticated()) {
                String email = loginRequest.getEmail();
                UserRoleEnum role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(grantedAuthority -> UserRoleEnum.valueOf(grantedAuthority.getAuthority()))
                    .orElse(UserRoleEnum.CUSTOMER);

                String token = jwtTokenProvider.createToke(email, role);  // JWT 토큰 생성
                log.info("JWT 토큰 생성 완료: {}", token);
                response.setHeader("Authorization", "Bearer " + token);  // 응답 헤더에 JWT 토큰 추가
            }

            log.info(authentication.toString());
            return authentication;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new JwtTokenException(591, "JWT 인증 토큰 파싱 과정에서 문제가 발생했습니다.");
        }
    }
}
