package friends.aidelivery.store.application;

import friends.aidelivery.store.application.dto.request.StoreRequestDto;
import friends.aidelivery.store.application.dto.response.RegionResponseDto;
import friends.aidelivery.store.application.dto.response.StoreCategoryResponseDto;
import friends.aidelivery.store.application.dto.response.StoreResponseDto;
import friends.aidelivery.store.domain.Region;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.StoreCategory;
import friends.aidelivery.store.domain.StoreCategoryMapping;
import friends.aidelivery.store.domain.repository.RegionRepository;
import friends.aidelivery.store.domain.repository.StoreCategoryMappingRepository;
import friends.aidelivery.store.domain.repository.StoreCategoryRepository;
import friends.aidelivery.store.domain.repository.StoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final RegionRepository regionRepository;
    private final StoreCategoryMappingRepository storeCategoryMappingRepository;


    public StoreResponseDto createStore(StoreRequestDto requestDto) {

        //UUID ownerId = requestDto.owner();

        List<Region> regionList = getRegionList(requestDto.region());
        List<StoreCategory> storeCategoryList = getStoreCategoryList(requestDto.storeCategory());

        String name = requestDto.name();
        String address = requestDto.address();
        String number = requestDto.number();

        Store store = new Store(name, address, number);
        store.addCategories(storeCategoryList);
        store.addRegions(regionList);

        Store saved = storeRepository.save(store);
        return StoreResponseDto.of(saved);
    }


    public Page<StoreResponseDto> getStoresByCategory(UUID uuid, String sortBy, int page, int size,
        boolean isAsc) {

        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        List<StoreCategoryMapping> categoryMappingList = storeCategoryMappingRepository.findByStoreCategoryId(
            uuid);

        List<Store> storeList = categoryMappingList.stream()
            .map(StoreCategoryMapping::getStore)
            .toList();

        int start = Math.min((int) pageable.getOffset(), storeList.size());
        int end = Math.min((start + pageable.getPageSize()), storeList.size());
        List<StoreResponseDto> pagedStoreList = storeList.subList(start, end).stream()
            .map(StoreResponseDto::of)
            .toList();

        return new PageImpl<>(pagedStoreList, pageable, storeList.size());
    }


    public StoreResponseDto updateStore(UUID storeId, StoreRequestDto requestDto) {
        Store store = storeRepository.findById(storeId).orElseThrow();

        List<Region> regionList = getRegionList(requestDto.region());
        List<StoreCategory> storeCategoryList = getStoreCategoryList(requestDto.storeCategory());
        String name = requestDto.name();
        String address = requestDto.address();
        String number = requestDto.number();

        store.updateName(name);
        store.updateAddress(address);
        store.updateNumber(number);

        store.updateRegions(regionList);
        store.updateCategories(storeCategoryList);

        Store updatedStore = storeRepository.save(store);

        return StoreResponseDto.of(updatedStore);
    }

    public Page<StoreResponseDto> getStore(String keyword, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Store> storePage = storeRepository.findByName(keyword, pageable);

        return storePage.map(StoreResponseDto::of);
    }

    private List<Region> getRegionList(List<UUID> regionIdList) {
        List<Region> regionList = new ArrayList<>();
        for (UUID index : regionIdList) {
            Region regionIndex = regionRepository.findById(index)
                .orElseThrow(() -> new RuntimeException("Region Not Found"));
            regionList.add(regionIndex);
        }
        return regionList;
    }

    private List<StoreCategory> getStoreCategoryList(List<UUID> storeCategoryIdList) {
        List<StoreCategory> storeCategoryList = new ArrayList<>();
        for (UUID index : storeCategoryIdList) {
            StoreCategory categoryIndex = storeCategoryRepository.findById(index)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
            storeCategoryList.add(categoryIndex);
        }
        return storeCategoryList;
    }

    public RegionResponseDto createRegion(String regionName) {
        Region region = new Region(regionName);
        Region saved = regionRepository.save(region);

        return RegionResponseDto.of(saved);
    }

    public StoreCategoryResponseDto createCategory(String categoryName) {
        StoreCategory storeCategory = new StoreCategory(categoryName);
        StoreCategory saved = storeCategoryRepository.save(storeCategory);

        return StoreCategoryResponseDto.of(saved);
    }
}
