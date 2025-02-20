package friends.aidelivery.admin.application.dto.response;

import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.enums.UserRoleEnum;

public record AdminUserRequestDto(
        String email,
        String nickname,
        UserRoleEnum role
) {
    public static AdminUserRequestDto of(User user) {
        return new AdminUserRequestDto(user.getEmail().getValue(), user.getNickname().getValue(), user.getRole());
    }

}



