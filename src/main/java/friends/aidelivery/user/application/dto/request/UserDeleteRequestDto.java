package friends.aidelivery.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {

    @NotBlank(message = "암호를 입력해 주세요.")
    private String password;
}
