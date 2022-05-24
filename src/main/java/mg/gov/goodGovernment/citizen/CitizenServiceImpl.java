package mg.gov.goodGovernment.citizen;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.authentication.ApplicationUser;
import mg.gov.goodGovernment.citizen.dataValidation.CitizenValidationResult;
import mg.gov.goodGovernment.security.AppUserRole;
import mg.gov.goodGovernment.security.Sha256;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static mg.gov.goodGovernment.citizen.dataValidation.CitizenRegistrationValidator.*;
import static mg.gov.goodGovernment.citizen.dataValidation.CitizenValidationResult.SUCCESS;

/**
 * Une implémentation des interfaces des services liées à l'entité Citizen
 *
 * @author Mandresy
 */
@Service
@AllArgsConstructor
public class CitizenServiceImpl implements CitizenService, CitizenUserDetailsService {
    private final CitizenRepository citizenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Savoir si un citoyen est majeur.
     * @param citizen un citoyen
     * @return <code>true</code> si le citoyen est majeur vrai<br>
     */
    public Boolean isAdultCitizen(Citizen citizen) {
        return citizen.getAge() >= 18;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Avoir le citoyen du base de données correspondant à l'email entré
        Citizen citizen = citizenRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Aucun citoyen n'a " + email + " comme email")
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

        // Validation des données d'insrcription du citoyen
        CitizenValidationResult registrationDataValidationResult = isPasswordContainsNoAccentedCharacter()
                .and(isPasswordContainsEightOrMoreWords())
                .and(isValidEmail())
                .and(isCitizenAdultAndHaveId())
                .and(isCitizenChildAndHaveNoId())
                .apply(citizen);
        if(!SUCCESS.equals(registrationDataValidationResult)) {
            throw new IllegalStateException( registrationDataValidationResult.getResult() );
        }

        // Un email est unique
        if(citizenRepository.existsByEmail(citizen.getEmail())) {
            throw new IllegalStateException("Un autre citoyen a déjà la même email");
        }

        // Cryptage du mdp en sha256
        citizen.setPassword( Sha256.hash(citizen.getPassword()) );

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
            CitizenValidationResult dataValidatorResult = isCitizenAdultAndHaveId().apply(newCitizenData);
            if( !SUCCESS.equals(dataValidatorResult) )
                throw new IllegalStateException(dataValidatorResult.getResult());

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
            CitizenValidationResult dataValidatorResult = isValidEmail().apply(newCitizenData);
            if( !SUCCESS.equals(dataValidatorResult) )
                throw new IllegalStateException(dataValidatorResult.getResult());

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

    @Override
    public Citizen findByEmail(String email) {
        return citizenRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Aucun citoyen n'a %s comme email", email))
        );
    }
}