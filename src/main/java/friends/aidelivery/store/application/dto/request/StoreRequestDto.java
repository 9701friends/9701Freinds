package friends.aidelivery.store.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

public record StoreRequestDto(
    @NotBlank(message = "가게이름은 필수입니다.") String name,
    @NotBlank(message = "지역은 필수입니다.") List<UUID> region,
    @NotBlank(message = "카테고리는 필수입니다.") List<UUID> storeCategory,
    @NotBlank(message = "가게번호는 필수입니다.") String number,
    @NotBlank(message = "소유주 등록은 필수입니다.") Long owner,
    @NotBlank(message = "가게주소는 필수입니다.") String address
){}
