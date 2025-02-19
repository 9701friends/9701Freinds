package friends.aidelivery.user.application.dto.response;

import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.enums.UserRoleEnum;

public record UserCreateResponse(
    String name,
    String email,
    String nickname,
    UserRoleEnum role,
    String phone,
    String address,
    Long id // 임시
) {
    public static UserCreateResponse of(User user){
        return new UserCreateResponse(
            user.getName().getValue(),
            user.getEmail().getValue(),
            user.getNickname().getValue(),
            user.getRole(),
            user.getPhone().getValue(),
            user.getAddress().getValue(),
            user.getId());
    }

}
