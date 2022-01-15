package mg.gov.goodGovernment.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.security.Sha256;

/**
 * Class de mapping d'un authetification Json<br>
 * <u>Exemple</u>:<br>
 * {<br>
 *   "username": "jean",<br>
 *   "password": "1234"<br>
 * }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAndPasswordRequest {

    private String username;
    private String password;

    /**
     * Hasher en sha256 le mot de passe
     */
    public void hashPassword() {
        this.password = Sha256.hash(this.password);
    }

}
