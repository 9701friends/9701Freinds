package friends.aidelivery.common.infrastructure.security.filter;


import static friends.aidelivery.common.infrastructure.security.JwtTokenProvider.AUTHORIZATION_KEY;

import friends.aidelivery.common.infrastructure.security.JwtTokenProvider;
import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        String token = jwtTokenProvider.getJwtFromHeader(request);
        log.info(token);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.getUserInfoFromToken(token);
            String email = claims.getSubject();
            Long userId = claims.get("userId", Long.class);
            String roleString = claims.get(AUTHORIZATION_KEY, String.class);
            UserRoleEnum role = UserRoleEnum.valueOf(roleString);

            UserDetails userDetails = new UserDetailsImpl(email, userId, role, null);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}