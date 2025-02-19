package friends.aidelivery.user.application.dto.request;

import friends.aidelivery.user.domain.enums.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
    @NotBlank(message = "이름은 필수입니다.") String name,
    @NotBlank(message = "이메일은 필수입니다.") String email,
    @NotBlank String nickname,
    @NotBlank(message = "비밀번호는 필수입니다.") String password,
    @NotNull(message = "권한은 필수입니다.") UserRoleEnum role,
    @NotBlank(message = "휴대 전화 번호는 필수입니다.") String phone,
    @NotBlank(message = "주소는 필수입니다.") String address) {

}
