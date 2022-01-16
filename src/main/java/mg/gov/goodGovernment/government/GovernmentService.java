package mg.gov.goodGovernment.government;

import java.util.List;

public interface GovernmentService {
    void createGovernment(Government government);
    List<Government> findAll();
}