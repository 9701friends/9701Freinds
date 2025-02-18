package friends.aidelivery.store.application.dto.response;

import friends.aidelivery.store.domain.Region;
import java.util.UUID;

public record RegionResponseDto (
    UUID regionId,
    String name
){
    public static RegionResponseDto of(Region region){
        return new RegionResponseDto(region.getId(), region.getName().getValue());
    }
}
