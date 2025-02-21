package friends.aidelivery.product.application.dto.response;

import friends.aidelivery.product.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;

public record ProductSearchResponse(List<ProductResponse> products, int totalPages,
                                    long totalElements) {

    public static ProductSearchResponse of(Page<Product> productPages) {
        return new ProductSearchResponse(productPages.map(ProductResponse::of).toList(),
            productPages.getTotalPages(), productPages.getTotalElements());
    }
}
