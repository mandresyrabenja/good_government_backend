package mg.gov.goodGovernment.government;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GovernmentRepository extends JpaRepository<Government, Integer> {
    Boolean existsByLoginIgnoreCase(String login);

    Optional<Government> findByLoginIgnoreCase(String login);
}
