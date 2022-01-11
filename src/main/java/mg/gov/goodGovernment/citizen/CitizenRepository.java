package mg.gov.goodGovernment.citizen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen, String> {
}
