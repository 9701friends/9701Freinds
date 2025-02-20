package friends.aidelivery.store.presentation;


import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.store.application.StoreService;
import friends.aidelivery.store.application.dto.request.StoreRequestDto;
import friends.aidelivery.store.application.dto.response.RegionResponseDto;
import friends.aidelivery.store.application.dto.response.StoreCategoryResponseDto;
import friends.aidelivery.store.application.dto.response.StoreResponseDto;
import java.util.UUID;

import friends.aidelivery.user.domain.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/stores")
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Secured({UserRoleEnum.Authority.MANAGER, UserRoleEnum.Authority.MASTER})
    @PostMapping("/regions")
    public ResponseEntity<CommonResponse> createRegion(@RequestParam String region) {
        RegionResponseDto regionName = storeService.createRegion(region);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(regionName),
                HttpStatus.CREATED);
    }

    @Secured({UserRoleEnum.Authority.MANAGER, UserRoleEnum.Authority.MASTER})
    @PostMapping("/categories")
    public ResponseEntity<CommonResponse> createCategory(@RequestParam String category) {
        StoreCategoryResponseDto categoryName = storeService.createCategory(category);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(categoryName),
                HttpStatus.CREATED);
    }

    @Secured({UserRoleEnum.Authority.MANAGER, UserRoleEnum.Authority.MASTER, UserRoleEnum.Authority.OWNER})
    @PostMapping("")
    public ResponseEntity<CommonResponse> createStore(
            final @RequestBody StoreRequestDto requestDto) {
        StoreResponseDto response = storeService.createStore(requestDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
                HttpStatus.CREATED);
    }

    @GetMapping("/category")
    public ResponseEntity<CommonResponse> getStoresByCategory(
            @RequestParam UUID categoryUUID,
            @RequestParam String sortBy,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam boolean isAsc) {

        Page<StoreResponseDto> pageResponse = storeService.getStoresByCategory(categoryUUID,
                sortBy, page, size, isAsc);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(pageResponse),
                HttpStatus.OK);
    }

    @Secured({UserRoleEnum.Authority.MANAGER, UserRoleEnum.Authority.MASTER, UserRoleEnum.Authority.OWNER})
    @PutMapping("/{storeId}")
    public ResponseEntity<CommonResponse> updateStore(@PathVariable UUID storeId, final @RequestBody StoreRequestDto requestDto) {
        StoreResponseDto response = storeService.updateStore(storeId, requestDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<CommonResponse> getStore(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean isAsc) {
        Page<StoreResponseDto> response = storeService.getStore(keyword, page, size, sortBy, isAsc);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({UserRoleEnum.Authority.MANAGER, UserRoleEnum.Authority.MASTER, UserRoleEnum.Authority.OWNER})
    @DeleteMapping("/{store_id}")
    public ResponseEntity<CommonResponse> deleteStore(@PathVariable UUID store_id) {
        StoreResponseDto response = storeService.deleteStore(store_id);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({UserRoleEnum.Authority.MANAGER, UserRoleEnum.Authority.MASTER, UserRoleEnum.Authority.OWNER})
    @DeleteMapping("/{region_id}")
    public ResponseEntity<CommonResponse> deleteRegion(@PathVariable UUID region_id) {
        RegionResponseDto response = storeService.deleteRegion(region_id);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @Secured({UserRoleEnum.Authority.MANAGER, UserRoleEnum.Authority.MASTER, UserRoleEnum.Authority.OWNER})
    @DeleteMapping("/{category_id}")
    public ResponseEntity<CommonResponse> deleteCategory(@PathVariable UUID category_id) {
        StoreCategoryResponseDto response = storeService.deleteStoreCategory(category_id);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }
}
