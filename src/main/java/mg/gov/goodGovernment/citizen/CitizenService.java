package mg.gov.goodGovernment.citizen;

import java.util.List;

/**
 * Service d'accès au base de données de l'entité Citizen
 *
 * @author Mandresy
 */
public interface CitizenService {
    void createCitizen(Citizen citizen);
    void updateCitizen(Long id, Citizen newCitizenData);
    List<Citizen> findAllCitizens();
    Citizen findCitizen(Long id);
    void deleteCitizen(Long id);
    Citizen findByEmail(String email);
}
