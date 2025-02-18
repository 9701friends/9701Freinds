package friends.aidelivery.store.infrastructure.jpa;

import friends.aidelivery.store.domain.Store;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreJpaRepository extends JpaRepository<Store, UUID> {

    @Query("SELECT s FROM Store s WHERE LOWER(s.name.value) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Store> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);
}
