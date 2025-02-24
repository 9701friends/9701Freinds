package friends.aidelivery.store.domain;

import friends.aidelivery.store.domain.vo.Name;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "p_store_category")
@Entity
public class StoreCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_category_id")
    private UUID id;

    @OneToMany(mappedBy = "storeCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategoryMapping> storeCategoryMappingList = new ArrayList<>();

    @Embedded
    private Name name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public StoreCategory(String value) {
        this.name = new Name(value);
        this.isDeleted = false;
    }

}
