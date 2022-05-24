package mg.gov.goodGovernment.region;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de l'entité Region
 *
 * @author Mandresy
 *
 */
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Boolean existsByName(String name);

    List<Region> findByOrderByName(Pageable pageable);

    Optional<Region> findByNameIgnoreCase(String name);
}
