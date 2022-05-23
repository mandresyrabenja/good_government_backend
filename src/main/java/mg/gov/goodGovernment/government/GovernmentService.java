package mg.gov.goodGovernment.government;

import java.util.List;

/**
 * Interface du service d'accès au base de données de l'entité Government
 *
 * @author Mandresy
 */
public interface GovernmentService {
    void createGovernment(Government government);
    List<Government> findAll();
}