package friends.aidelivery.product.application.dto.response;

import friends.aidelivery.product.domain.ProductCategory;
import java.util.List;
import org.springframework.data.domain.Page;

public record ProductCategorySearchResponse(List<ProductCategoryResponse> productCategories,
                                            int totalPages,
                                            long totalElements) {

    public static ProductCategorySearchResponse of(Page<ProductCategory> productCategoryPages) {
        return new ProductCategorySearchResponse(
            productCategoryPages.map(ProductCategoryResponse::of).toList(),
            productCategoryPages.getTotalPages(), productCategoryPages.getTotalElements());
    }
}
