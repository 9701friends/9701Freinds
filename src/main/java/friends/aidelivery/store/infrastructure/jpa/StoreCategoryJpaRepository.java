package friends.aidelivery.store.infrastructure.jpa;

import friends.aidelivery.store.domain.StoreCategory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreCategoryJpaRepository extends JpaRepository<StoreCategory, UUID> {

}
