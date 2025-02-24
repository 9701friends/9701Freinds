package friends.aidelivery.store.domain;

import friends.aidelivery.common.domain.TimeStamp;
import friends.aidelivery.store.domain.vo.Address;
import friends.aidelivery.store.domain.vo.Name;
import friends.aidelivery.store.domain.vo.Rating;
import friends.aidelivery.store.domain.vo.StoreNumber;
import friends.aidelivery.user.domain.User;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_store")
@NoArgsConstructor
public class Store extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id")
    private UUID id;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreCategoryMapping> storeCategoryMappingList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreRegionMapping> storeRegionMappingList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @Embedded
    private Name name;

    @Embedded
    private Address address;

    @Embedded
    private StoreNumber storeNumber;

    @Embedded
    private Rating averageRating;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Store(String name, String address, String storeNumber, User user) {
        this.name = new Name(name);
        this.address = new Address(address);
        this.storeNumber = new StoreNumber(storeNumber);
        this.user = user;
        this.storeCategoryMappingList = new ArrayList<>();
        this.storeRegionMappingList = new ArrayList<>();
        this.averageRating = new Rating(BigDecimal.ZERO, 0);
        this.isDeleted = false;
    }

    public void CalculateRating(int quantityChange, BigDecimal oldRating, BigDecimal newRating) {
        this.averageRating = this.averageRating.update(quantityChange, oldRating, newRating);
    }

    public void addCategories(List<StoreCategory> storeCategoryList) {
        for (StoreCategory index : storeCategoryList) {
            StoreCategoryMapping mapping = new StoreCategoryMapping(this, index);
            this.storeCategoryMappingList.add(mapping);
        }
    }

    public void addRegions(List<Region> regionList) {
        for (Region index : regionList) {
            StoreRegionMapping mapping = new StoreRegionMapping(this, index);
            this.storeRegionMappingList.add(mapping);
        }
    }

    public void updateName(String newName) {
        this.name.update(newName);
    }

    public void updateAddress(String newAddress) {
        this.address.update(newAddress);
    }

    public void updateNumber(String newNumber) {
        this.storeNumber.update(newNumber);
    }

    public void updateCategories(List<StoreCategory> categoryList) {
        this.storeCategoryMappingList.clear();
        addCategories(categoryList);
    }

    public void updateRegions(List<Region> regionList) {
        this.storeRegionMappingList.clear();
        addRegions(regionList);
    }
}
