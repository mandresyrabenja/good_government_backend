package mg.gov.goodGovernment.government;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.gov.goodGovernment.security.Sha256;

import javax.persistence.*;

/**
 * Entité du table government
 *
 * @author Mandresy
 */
@Data
@NoArgsConstructor
@Entity
public class Government {
    @Id
    @SequenceGenerator(
            name = "sequence_government",
            sequenceName = "sequence_government"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_government"
    )
    private Integer id;

    @Column(unique = true)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter(AccessLevel.NONE)
    private String password;

    public Government(String login, String password) {
        this.login = login;
        setPassword(password);
    }

    /**
     * Setter avec hash du mot de passe en sha256 avant de faire l'affectation
     * @param password Mot de passe
     */
    public void setPassword(String password) {
        this.password = Sha256.hash(password);
    }
}
