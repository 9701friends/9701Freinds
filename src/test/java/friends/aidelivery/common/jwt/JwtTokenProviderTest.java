package friends.aidelivery.common.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import friends.aidelivery.common.exception.JwtTokenException;
import friends.aidelivery.common.infrastructure.security.JwtTokenProvider;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtTokenProviderTest {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private String base64EncodedSecretKey;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeAll
    public void beforeAll() {
        base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Test
    @DisplayName("secretKey Base64 인코딩 테스트")
    void setBase64EncodedSecretKey() {
        System.out.println(base64EncodedSecretKey);
        assertThat(
            new String(Base64.getDecoder().decode(base64EncodedSecretKey), StandardCharsets.UTF_8))
            .isEqualTo(secretKey);
    }

    @Test
    @DisplayName("토큰 정상 발급 테스트")
    void generateToken() {
        //given
        String email = "jwtTest";
        Long userId = 1L;
        UserRoleEnum role = UserRoleEnum.CUSTOMER;

        //when
        String token = jwtTokenProvider.createToken(email, role, userId);

        //then
        assertNotNull(token, "토큰이 비어 있지 않습니다.");
        assertTrue(token.startsWith("eyJ"),
            "발급된 토큰이 JWT 형식으로 시작합니다.");
    }

    @Test
    @DisplayName("발급된 토큰이 검증이 잘 되는지 테스트")
    void validateToken() {
        //given
        String email = "jwtTest";
        Long userId = 1L;
        UserRoleEnum role = UserRoleEnum.CUSTOMER;
        String accessToken = jwtTokenProvider.createToken(email, role, userId);

        //when, then
        assertDoesNotThrow(() -> jwtTokenProvider.validateToken(accessToken));
    }

    @Test
    @DisplayName("이상한 문자열이 들어오면 예외가 발생하는지 확인")
    void validateStringToken() {
        //given
        String token = "string";

        //when
        Exception exception = assertThrows(JwtTokenException.class, () -> {
            jwtTokenProvider.validateToken(token);
        });
        assertEquals("유효하지 않은 JWT 토큰입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("토큰의 만료 시간이 1일 후로 설정되는지 확인")
    void testTokenExpirationTime() {
        // given
        String email = "jwtTest";
        Long userId = 1L;
        UserRoleEnum role = UserRoleEnum.CUSTOMER;

        // when
        String token = jwtTokenProvider.createToken(email, role, userId);
        Date expiration = jwtTokenProvider.getUserInfoFromToken(token)
            .getExpiration(); // 토큰에서 만료 시간 추출

        // then
        Date currentDate = new Date();
        long expirationTime = expiration.getTime() - currentDate.getTime();

        // 만료 시간이 1일 (86400초 * 1000 밀리초) 내에 설정되어 있는지 확인
        assertTrue(expirationTime > 0 && expirationTime <= 86400 * 1000,
            "토큰 만료 시간이 1일 이내로 설정되지 않았습니다.");
    }

    /*
    테스트를 위해 만료 기간 설정을 변경하고 테스트했음, 통과 확인
    @Test
    @DisplayName("만료된 토큰을 검증하면 예외가 발생하는지 확인")
    void testExpiredToken() throws InterruptedException {
        // given
        String email = "jwtTest";
        Long userId = 1L;
        UserRoleEnum role = UserRoleEnum.CUSTOMER;
        String token = jwtTokenProvider.createToken(email, role, userId);

        // When
        Thread.sleep(3000);

        // then
        Exception exception = assertThrows(JwtTokenException.class, () -> {
            jwtTokenProvider.validateToken(token);  // 만료된 토큰을 검증하면 예외가 발생해야 한다
        });
        assertEquals("유효하지 않은 JWT 토큰입니다.", exception.getMessage());
    }
    */

}