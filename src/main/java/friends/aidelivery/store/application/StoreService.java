package friends.aidelivery.store.application;

import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.store.application.dto.request.StoreRequestDto;
import friends.aidelivery.store.application.dto.response.RegionResponseDto;
import friends.aidelivery.store.application.dto.response.StoreCategoryResponseDto;
import friends.aidelivery.store.application.dto.response.StoreResponseDto;
import friends.aidelivery.store.domain.Region;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.StoreCategory;
import friends.aidelivery.store.domain.repository.RegionRepository;
import friends.aidelivery.store.domain.repository.StoreCategoryMappingRepository;
import friends.aidelivery.store.domain.repository.StoreCategoryRepository;
import friends.aidelivery.store.domain.repository.StoreRegionMappingRepository;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.store.exception.StoreNotFoundException;
import friends.aidelivery.store.infrastructure.jpa.StoreRegionMappingJpaRepository;
import friends.aidelivery.user.domain.User;
import friends.aidelivery.user.domain.repository.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private static final Logger log = LoggerFactory.getLogger(StoreService.class);
    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final RegionRepository regionRepository;
    private final StoreCategoryMappingRepository storeCategoryMappingRepository;
    private final UserRepository userRepository;
    private final StoreRegionMappingRepository storeRegionMappingRepository;

    public StoreResponseDto createStore(UserDetailsImpl userDetails, StoreRequestDto requestDto) {
        /**
         * TODO 중복된 전화번호, 이름 등록불가 , 유저 연관관계 설정
         */
        if (!userDetails.getUserId().equals(requestDto.owner())) {
            throw new RuntimeException("유저 정보가 일치하지 않습니다.");
        }

        List<Region> regionList = getRegionList(requestDto.region());
        List<StoreCategory> storeCategoryList = getStoreCategoryList(requestDto.storeCategory());

        String name = requestDto.name();
        String address = requestDto.address();
        String number = requestDto.number();
        User user = userRepository.findById(requestDto.owner())
            .orElseThrow(() -> new RuntimeException("User Not Found"));
        Store store = new Store(name, address, number, user);
        store.addCategories(storeCategoryList);
        store.addRegions(regionList);

        Store saved = storeRepository.save(store);
        return StoreResponseDto.of(saved);
    }


    @Transactional(readOnly = true)
    public Page<StoreResponseDto> getStoresByCategory(UUID uuid, String sortBy, int page, int size,
        boolean isAsc) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Store> storePage = storeCategoryMappingRepository.findStoresByCategoryId(uuid, sortBy,
            isAsc, pageable);

        List<StoreResponseDto> responseDtoList = storePage.stream().map(StoreResponseDto::of)
            .toList();
        return new PageImpl<>(responseDtoList, pageable, storePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<StoreResponseDto> getStoresByRegion(UUID regionUUID, String sortBy, int page, int size, boolean isAsc) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Store> storePage = storeRegionMappingRepository.findStoresByRegionId(regionUUID,sortBy,isAsc,pageable);

        List<StoreResponseDto> responseDtoList = storePage.stream().map(StoreResponseDto::of).toList();
        return new PageImpl<>(responseDtoList, pageable, storePage.getTotalElements());
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public Page<StoreResponseDto> getStore(String keyword, int page, int size, String sortBy,
        boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Store> storePage = storeRepository.findByName(keyword, pageable);

        return storePage.map(StoreResponseDto::of);
    }

    @Transactional
    public RegionResponseDto createRegion(String regionName) {
        /**
         * TODO 중복된 Region 등록 불가
         */

        Region region = new Region(regionName);
        Region saved = regionRepository.save(region);

        return RegionResponseDto.of(saved);
    }

    @Transactional
    public StoreCategoryResponseDto createCategory(String categoryName) {
        /**
         * // TODO 중복된 Category 등록 불가
         */
        StoreCategory storeCategory = new StoreCategory(categoryName);
        StoreCategory saved = storeCategoryRepository.save(storeCategory);

        return StoreCategoryResponseDto.of(saved);
    }

    @Transactional
    public StoreResponseDto deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new RuntimeException("Store Not FOUND"));

        storeRepository.softDeleteStore(storeId);
        return StoreResponseDto.of(store);
    }

    @Transactional
    public RegionResponseDto deleteRegion(UUID regionId) {
        Region region = regionRepository.findById(regionId)
            .orElseThrow(() -> new RuntimeException("Region Not Found"));

        regionRepository.softDeleteRegion(regionId);
        return RegionResponseDto.of(region);
    }

    @Transactional
    public StoreCategoryResponseDto deleteStoreCategory(UUID storeCategoryId) {
        StoreCategory storeCategory = storeCategoryRepository.findById(storeCategoryId)
            .orElseThrow(() -> new RuntimeException("Store Category Not Found"));

        storeCategoryRepository.softDeleteCategory(storeCategoryId);
        return StoreCategoryResponseDto.of(storeCategory);
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

    @Transactional
    public void calculateRating(final UUID storeId, int quantityChange, BigDecimal oldRating,
        BigDecimal newRating) {
        final Store store = getStoreOrElseThrow(storeId);
        store.CalculateRating(quantityChange, oldRating, newRating);
    }

    @Transactional(readOnly = true)
    public Store getStoreOrElseThrow(UUID storeId) {
        return storeRepository.findById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(storeId));
    }


}
