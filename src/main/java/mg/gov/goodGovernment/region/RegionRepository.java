package mg.gov.goodGovernment.region;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    Boolean existsByName(String name);

    List<Region> findByOrderByName(Pageable pageable);

    Optional<Region> findByName(String name);

    Optional<Region> findByNameIgnoreCase(String name);
}
