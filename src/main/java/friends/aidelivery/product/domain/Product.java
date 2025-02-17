package friends.aidelivery.product.domain;

import friends.aidelivery.product.domain.enums.ProductStatus;
import friends.aidelivery.product.domain.vo.Content;
import friends.aidelivery.product.domain.vo.Name;
import friends.aidelivery.product.domain.vo.Price;
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
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Product(
        final ProductCategory productCategory,
        final Name name,
        final Content content,
        final Price price,
        final ProductStatus status
    ) {
        this.productCategory = productCategory;
        this.name = name;
        this.content = content;
        this.price = price;
        this.status = status;
    }

    public Product(final ProductCategory productCategory, final String name, final String content,
        final Long price,
        final ProductStatus status) {
        this.productCategory = productCategory;
        this.name = new Name(name);
        this.content = new Content(content);
        this.price = new Price(price);
        this.status = status;
    }

    public void updateName(final String value) {
        this.name = name.update(value);
    }

    public void updateContent(final String value) {
        this.content = content.update(value);
    }

    public void updatePrice(final Long value) {
        this.price = price.update(value);
    }

    public void updateStatus(final ProductStatus status) {
        this.status = status;
    }
}
