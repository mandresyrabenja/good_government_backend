package mg.gov.goodGovernment.citizen.dataValidation;

import mg.gov.goodGovernment.citizen.Citizen;

import java.util.function.Function;
import java.util.regex.Pattern;

import static mg.gov.goodGovernment.citizen.dataValidation.CitizenValidationResult.*;

/**
 * Vérificateur des données d'inscription d'un citoyen
 *
 * @author Mandresy
 */
public interface CitizenRegistrationValidator extends Function<Citizen, CitizenValidationResult> {
    // Format d'un email valide
    final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w-]{2,}+\\.[a-z]{2,4}$");
    // Format d'un String qui contient un caractère accentué
    final Pattern ACCENTED_PATTERN = Pattern.compile("(.)*[À-ú](.)*");

    /**
     * Méthode de combinaison pour permetre d'utiliser du combinator pattern sur cet validation des données
     * d'inscription de citoyen.
     * @param other Un autre validation
     * @return Resultat du validation
     */
    default CitizenRegistrationValidator and(CitizenRegistrationValidator other) {
        return citizen -> {
            CitizenValidationResult result = this.apply(citizen);
            return (SUCCESS.equals(result)) ? other.apply(citizen) : result;
        };
    }

    public static CitizenRegistrationValidator isPasswordContainsEightOrMoreWords() {
        return citizen -> (citizen.getPassword().length() >= 8) ? SUCCESS : PASSWORD_TOO_SHORT;
    }

    static CitizenRegistrationValidator isPasswordContainsNoAccentedCharacter() {
        return citizen -> ACCENTED_PATTERN.matcher(citizen.getPassword()).matches() ? ACCENTED_PASSWORD : SUCCESS;
    }

    static CitizenRegistrationValidator isValidEmail() {
        return citizen -> EMAIL_PATTERN.matcher(citizen.getEmail()).matches() ? SUCCESS : INVALID_EMAIL;
    }

    static CitizenRegistrationValidator isCitizenAdultAndHaveId() {
        return citizen -> (citizen.getAge() >= 18) && (null == citizen.getCin()) ? HAVE_NO_ID : SUCCESS;
    }

    static CitizenRegistrationValidator isCitizenChildAndHaveNoId() {
        return citizen -> (citizen.getAge() < 18) && (null != citizen.getCin()) ? HAVE_AN_ID : SUCCESS;
    }
}
