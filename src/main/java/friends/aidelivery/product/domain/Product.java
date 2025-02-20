package friends.aidelivery.product.domain;

import friends.aidelivery.product.application.dto.request.ProductUpdateRequest;
import friends.aidelivery.product.domain.enums.ProductStatus;
import friends.aidelivery.product.domain.vo.Content;
import friends.aidelivery.product.domain.vo.Name;
import friends.aidelivery.product.domain.vo.Price;
import friends.aidelivery.product.exception.ProductNotSellingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "p_product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;

    @Embedded
    private Name name;

    @Embedded
    private Content content;

    @Embedded
    private Price price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Product(final ProductCategory productCategory, final String name, final String content,
        final Long price,
        final ProductStatus status) {
        this.productCategory = productCategory;
        this.name = new Name(name);
        this.content = new Content(content);
        this.price = new Price(price);
        this.status = status;
    }

    public void update(final ProductUpdateRequest newProduct) {
        this.name = name.update(newProduct.name());
        this.content = content.update(newProduct.content());
        this.price = price.update(newProduct.price());
    }

    public void updateCategory(final ProductCategory newProductCategory) {
        this.productCategory.removeProduct(this);
        this.productCategory = newProductCategory;
        this.productCategory.addProduct(this);
    }

    public void updateStatus(final ProductStatus status) {
        this.status = status;
    }

    public void softDelete() {
        this.status = ProductStatus.HOLD;
        this.deletedAt = LocalDateTime.now();
        this.productCategory.removeProduct(this);
    }

    public void checkSelling() {
        if (status != ProductStatus.SELLING) {
            throw new ProductNotSellingException(this.id);
        }
    }
}
