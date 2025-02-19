package friends.aidelivery.user.application.dto.response;

import friends.aidelivery.user.domain.User;

public record UserResponseDto(
    String email,
    String nickname
) {
    public static UserResponseDto of(User user){
        return new UserResponseDto(user.getEmail().getValue(), user.getNickname().getValue());
    }
}
