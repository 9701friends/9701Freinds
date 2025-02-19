package friends.aidelivery.user.application.dto.response;

import friends.aidelivery.user.domain.User;

public record UserLoginResponse(
    String email,
    String nickname
) {
    public static UserLoginResponse of(User user){
        return new UserLoginResponse(user.getEmail().getValue(), user.getNickname().getValue());
    }
}
