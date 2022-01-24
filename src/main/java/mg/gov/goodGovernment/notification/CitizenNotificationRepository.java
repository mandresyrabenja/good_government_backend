package mg.gov.goodGovernment.notification;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CitizenNotificationRepository extends MongoRepository<CitizenNotification, Long> {
    @Query("{'citizen.email': ?0}")
    Optional<CitizenNotification> findByCitizenEmail(@Param("email") String email);
}
