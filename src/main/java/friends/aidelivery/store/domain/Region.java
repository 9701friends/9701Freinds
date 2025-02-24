package friends.aidelivery.store.domain;

import friends.aidelivery.store.domain.vo.Name;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "p_region")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "region_id")
    private UUID id;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreRegionMapping> storeRegionMappingList = new ArrayList<>();

    @Embedded
    private Name name;

    @Column(name ="is_deleted")
    private boolean isDeleted;

    public Region (String value){
        this.name = new Name(value);
        this.isDeleted = false;
    }

}
