package mg.gov.goodGovernment.region;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mg.gov.goodGovernment.report.Report;
import mg.gov.goodGovernment.security.Sha256;

import javax.persistence.*;
import java.util.Collection;

/**
 * Entité du table region. Le table région contient les comptes de type région.
 *
 * @author Mandresy
 */
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

    @Column(nullable = false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter(AccessLevel.NONE)
    private String password;

    @org.springframework.data.annotation.Transient
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("region_report")
    private Collection<Report> reports;

    public Region(String name, String password) {
        this.name = name;
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