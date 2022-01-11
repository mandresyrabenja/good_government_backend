package mg.gov.goodGovernment.government;

import mg.gov.goodGovernment.http.HttpResponse;
import org.springframework.http.ResponseEntity;

public interface GovernmentService {
    public void addGovernment(Government government);
}