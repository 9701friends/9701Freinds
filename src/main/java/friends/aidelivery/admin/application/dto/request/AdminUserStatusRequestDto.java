package friends.aidelivery.admin.application.dto.request;

import friends.aidelivery.user.domain.enums.UserRoleEnum;

public record AdminUserStatusRequestDto(
        UserRoleEnum role
) {
}
