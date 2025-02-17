package friends.aidelivery.product.presentation;

import friends.aidelivery.common.application.dto.CommonResponse;
import friends.aidelivery.common.util.ResponseVOUtils;
import friends.aidelivery.product.application.ProductService;
import friends.aidelivery.product.application.dto.request.ProductCategoryCreateRequest;
import friends.aidelivery.product.application.dto.request.ProductCreateRequest;
import friends.aidelivery.product.application.dto.response.ProductCategoryResponse;
import friends.aidelivery.product.application.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<CommonResponse> createProduct(
        final @RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.createProduct(request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }

    @PostMapping("/category")
    public ResponseEntity<CommonResponse> createProductCategory(
        final @RequestBody ProductCategoryCreateRequest request) {
        ProductCategoryResponse response = productService.createProductCategory(request);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response),
            HttpStatus.CREATED);
    }
}
