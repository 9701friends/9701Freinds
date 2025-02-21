package friends.aidelivery.product.domain;

import friends.aidelivery.common.domain.TimeStamp;
import friends.aidelivery.product.application.dto.request.ProductCategoryUpdateRequest;
import friends.aidelivery.product.domain.vo.Content;
import friends.aidelivery.product.domain.vo.Name;
import friends.aidelivery.store.domain.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_product_category")
@Entity
public class ProductCategory extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_category_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "store_id")
    private Store store;

    @Embedded
    private Name name;

    @Embedded
    private Content content;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public ProductCategory(final Store store, final String name, final String content) {
        this.store = store;
        this.name = new Name(name);
        this.content = new Content(content);
        this.isDeleted = false;
    }

    public void update(final ProductCategoryUpdateRequest newProductCategory) {
        this.name = name.update(newProductCategory.name());
        this.content = content.update(newProductCategory.content());
    }

    public void addProduct(final Product product) {
        this.products.add(product);
    }

    public void removeProduct(final Product product) {
        this.products.remove(product);
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}
