package friends.aidelivery.store.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_store_category_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class StoreCategoryMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_category_mapping_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id",nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_category_id",nullable = false)
    private StoreCategory storeCategory;

    @Column(name ="is_deleted")
    private boolean isDeleted;

    public StoreCategoryMapping(Store store, StoreCategory storeCategory) {
        this.store = store;
        this.storeCategory = storeCategory;
        this.isDeleted = false;
    }

}
