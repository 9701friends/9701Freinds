package friends.aidelivery.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import friends.aidelivery.auth.jwt.JwtTokenProvider;
import friends.aidelivery.auth.jwt.application.dto.UserDetailsImpl;
import friends.aidelivery.auth.jwt.exception.JwtTokenException;
import friends.aidelivery.user.application.dto.request.UserLoginRequest;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new JwtTokenException(591, "JWT 인증 토큰 파싱 과정에서 문제가 발생했습니다.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        log.info("로그인 성공");
        String userEmail = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser()
            .getRole();

        String token = jwtTokenProvider.createToken(userEmail, role);
        jwtTokenProvider.addJwtToCookie(token,response);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}
