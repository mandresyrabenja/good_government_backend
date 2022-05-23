package mg.gov.goodGovernment.citizen.dataValidation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Liste des types de resultat du validation des données d'un citoyen
 *
 * @author Mandresy
 */
@RequiredArgsConstructor
@Getter
public enum CitizenValidationResult {
    SUCCESS("Email valide"),
    INVALID_EMAIL("Format d'email invalide"),
    PASSWORD_TOO_SHORT("Le mot de passe doit contenir au moins 8 caractères"),
    ACCENTED_PASSWORD("Le mot de passe ne doît pas contenir une lettre accentué"),
    HAVE_NO_ID("Un citoyen qui a plus de 18ans doit avoir un CIN"),
    HAVE_AN_ID("Un citoyen qui a moins de 18ans ne doit pas encore avoir un CIN");

    private final String result;
}
