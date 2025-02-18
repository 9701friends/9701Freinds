package friends.aidelivery.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "p_store_region_mapping")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoreRegionMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_region_mapping_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    public StoreRegionMapping(Store store, Region region){
        this.store = store;
        this.region = region;
    }

}
