package mg.gov.goodGovernment.citizen;

import java.util.List;

public interface CitizenService {
    void createCitizen(Citizen citizen);
    void updateCitizen(Long id, Citizen newCitizenData);
    List<Citizen> findAllCitizens();
    Citizen findCitizen(Long id);
    void deleteCitizen(Long id);
    Citizen findByEmail(String email);
}
