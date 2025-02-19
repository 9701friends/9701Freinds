package friends.aidelivery.product.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.product.application.ProductService;
import friends.aidelivery.product.application.dto.request.ProductCategoryCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductCategoryUpdateRequest;
import friends.aidelivery.product.application.dto.request.ProductCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductStatusUpdateRequest;
import friends.aidelivery.product.application.dto.request.ProductUpdateRequest;
import friends.aidelivery.product.application.dto.response.ProductCategoryResponse;
import friends.aidelivery.product.application.dto.response.ProductResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<CommonResponse> createProduct(
        @Valid @RequestBody final ProductCreateRequest request) {
        ProductResponse response = productService.createProduct(request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @PostMapping("/categories")
    public ResponseEntity<CommonResponse> createProductCategory(
        @Valid @RequestBody final ProductCategoryCreateRequest request) {
        ProductCategoryResponse response = productService.createProductCategory(request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CommonResponse> updateProduct(
        @PathVariable final UUID productId,
        @Valid @RequestBody final ProductUpdateRequest request) {
        ProductResponse response = productService.updateProduct(productId, request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }

    @PutMapping("/categories/{productCategoryId}")
    public ResponseEntity<CommonResponse> updateProductCategory(
        @PathVariable final UUID productCategoryId,
        @Valid @RequestBody final ProductCategoryUpdateRequest request) {
        ProductCategoryResponse response = productService.updateProductCategory(productCategoryId,
            request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.OK);
    }

    @PatchMapping("/{productId}/category")
    public ResponseEntity<CommonResponse> updateCategoryOfProduct(
        @PathVariable final UUID productId,
        @RequestParam final UUID productCategoryId) {
        ProductResponse response = productService.updateCategoryOfProduct(productId,
            productCategoryId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }

    @PatchMapping("/status")
    public ResponseEntity<CommonResponse> updateStatus(
        @RequestBody final ProductStatusUpdateRequest request) {
        productService.updateStatus(request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponse> softDeleteProducts(
        @RequestBody final List<UUID> productIds) {
        productService.softDeleteProducts(productIds);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/categories")
    public ResponseEntity<CommonResponse> softDeleteProductCategories(
        @RequestBody final List<UUID> productCategoryIds) {
        productService.softDeleteProductCategories(productCategoryIds);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse> getProduct(@PathVariable final UUID productId) {
        ProductResponse response = productService.getProductById(productId);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.OK);
    }
}
