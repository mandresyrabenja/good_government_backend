package mg.gov.goodGovernment.citizen;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.region.Region;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CitizenServiceImpl implements CitizenService{
    private final CitizenRepository citizenRepository;

    @Override
    public void createCitizen(Citizen citizen) {
        // L'ID du citoyen est 'name'+'firstname'+'cin'
        citizen.createId();
        // Vérification du doublure
        if(citizenRepository.existsById(citizen.getId())) {
            throw new IllegalStateException("Un citoyen porte déjà le même nom, prenom et CIN");
        } else {
            citizenRepository.save(citizen);
        }
    }

    @Override
    public void updateCitizen(String id, Long cin, String firstName, String lastName, LocalDate dob, String email, String password) {

    }

    @Override
    public List<Citizen> findAllCitizens() {
        return null;
    }

    @Override
    public Region findCitizen(String id) {
        return null;
    }
}
