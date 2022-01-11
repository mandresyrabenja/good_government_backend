package mg.gov.goodGovernment.citizen;

import mg.gov.goodGovernment.region.Region;

import java.time.LocalDate;
import java.util.List;

public interface CitizenService {
    public void createCitizen(Citizen citizen);
    public void updateCitizen(String id, Long cin, String firstName, String lastName, LocalDate dob, String email, String password);
    public List<Citizen> findAllCitizens();
    public Region findCitizen(String id);
}
