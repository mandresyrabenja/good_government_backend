package mg.gov.goodGovernment.citizen;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository du table citizen
 *
 * @author Mandresy
 */
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByEmail(String email);

    Boolean existsByEmail(String email);
}
