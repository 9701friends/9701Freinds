package friends.aidelivery.store.application.dto.response;

import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.StoreCategoryMapping;
import friends.aidelivery.store.domain.StoreRegionMapping;

import java.util.List;
import java.util.UUID;

public record StoreResponseDto(
        UUID storeId,
        List<UUID> storeCategoryList,
        List<UUID> storeRegionList,
        String name,
        String number,
        //String owner,
        String address,
        double rating,
        int reviewCount

) {
    public static StoreResponseDto of(Store store) {
        return new StoreResponseDto(
                store.getId(),
                store.getStoreCategoryMappingList().stream().map(StoreCategoryMapping::getId).toList(),
                store.getStoreRegionMappingList().stream().map(StoreRegionMapping::getId).toList(),
                store.getName().getValue(),
                store.getStoreNumber().getValue(),
                store.getAddress().getValue(),
                store.getAverageRating().getValue().doubleValue(),
                store.getAverageRating().getQuantity());
    }
}
