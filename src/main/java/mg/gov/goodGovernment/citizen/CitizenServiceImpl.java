package mg.gov.goodGovernment.citizen;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.security.AppUserRole;
import mg.gov.goodGovernment.authentication.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@AllArgsConstructor
public class CitizenServiceImpl implements CitizenService, UserDetailsService {
    private final CitizenRepository citizenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Avoir le citoyen du base de données correspondant à l'email entré
        Citizen citizen = citizenRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Aucun citoyen n'a cet email" + email)
        );

        // Créer un UserDetail à partir du citoyen obtenu
        return new ApplicationUser(
                citizen.getEmail(),
                passwordEncoder.encode( citizen.getPassword() ),
                AppUserRole.CITIZEN.getGrantedAutorities(),
                true,
                true,
                true,
                true
        );
    }

    @Override
    public void createCitizen(Citizen citizen) {
        // Vérification de l'attribut CIN si le citoyen a 18ans ou plus
        if(citizen.isAdultCitizen() && (null == citizen.getCin()) ) {
            throw new IllegalStateException("Un citoyen qui a 18ans ou plus doit avoir un CIN");
        }

        // Un citoyen doit être majeur pour avoir un CIN
        if(!citizen.isAdultCitizen() && (null != citizen.getCin()) ) {
            throw new IllegalStateException("Un citoyen doit être majeur pour avoir un CIN");
        }

        citizenRepository.save(citizen);
    }

    @Override
    @Transactional
    public void updateCitizen(Long id, Citizen newCitizenData) {
        // Avoir le citoyen correspondant
        Citizen dbCitizen = findCitizen(id);

        // Insertion des updates
        if(null != newCitizenData.getCin()) {
            // Un citoyen doit être majeur pour avoir un CIN
            if(!dbCitizen.isAdultCitizen()) throw new IllegalStateException("Un citoyen doit être majeur pour avoir un CIN");

            dbCitizen.setCin(newCitizenData.getCin());
        }
        if(null != newCitizenData.getDob()) {
            int age = Period.between(newCitizenData.getDob(), LocalDate.now()).getYears();

            // Vérification de l'attribut CIN si le citoyen a 18ans ou plus
            if((age >= 18) && (null == dbCitizen.getCin()) ) {
                throw new IllegalStateException("Un citoyen qui a 18ans ou plus doit avoir un CIN");
            }

            // Un citoyen doit être majeur pour avoir un CIN
            if((age < 18) && (null != dbCitizen.getCin()) ) {
                throw new IllegalStateException("Un citoyen doit être majeur pour avoir un CIN");
            }

            dbCitizen.setDob(newCitizenData.getDob());
        }
        if(null != newCitizenData.getEmail()) {
            dbCitizen.setEmail(newCitizenData.getEmail());
        }
        if(null != newCitizenData.getFirstName()) {
            dbCitizen.setFirstName(newCitizenData.getFirstName());
        }
        if(null != newCitizenData.getLastName()) {
            dbCitizen.setLastName(newCitizenData.getLastName());
        }
        if(null != newCitizenData.getPassword()) {
            dbCitizen.setPassword(newCitizenData.getPassword());
        }

    }

    @Override
    public List<Citizen> findAllCitizens() {
        return citizenRepository.findAll();
    }

    @Override
    public Citizen findCitizen(Long id) {
        return citizenRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Aucun citoyen n'a %d comme ID", id))
        );
    }

    @Override
    public void deleteCitizen(Long id) {
        Citizen dbCitizen = findCitizen(id);
        citizenRepository.delete(dbCitizen);
    }
}