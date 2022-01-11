package mg.gov.goodGovernment.government;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GovernmentServiceImpl implements GovernmentService{
    private final GovernmentRepository repository;

    @Override
    public void addGovernment(Government government) {
        if( !repository.existsByLogin(government.getLogin()) )
            repository.save(government);
        else
            throw new IllegalStateException("Cet login existe dejà");
    }
}
