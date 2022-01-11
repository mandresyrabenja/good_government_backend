package mg.gov.goodGovernment.region;

import com.google.common.hash.Hashing;
import lombok.*;
import mg.gov.goodGovernment.report.Report;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Region {
    @Id
    @SequenceGenerator(
            name = "sequence_region",
            sequenceName = "sequence_region"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_region"
    )
    private Integer id;
    private String name;
    @Setter(AccessLevel.NONE)
    private String password;
    @OneToMany(mappedBy = "region")
    private Collection<Report> reports;

    public Region(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Setter avec hash du mot de passe en sha256 avant de faire l'affectation
     * @param password Mot de passe
     */
    public void setPassword(String password) {
        this.password = Hashing.sha256().hashString(
                password, StandardCharsets.UTF_8
        ).toString();
    }
}