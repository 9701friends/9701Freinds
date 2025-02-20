package friends.aidelivery.admin.application.dto.response;

import friends.aidelivery.user.domain.User;

public record AdminUserUpdateResponse (
        Long id
){
    public static AdminUserUpdateResponse of(User user) {
        return new AdminUserUpdateResponse(user.getId());
    }
}
