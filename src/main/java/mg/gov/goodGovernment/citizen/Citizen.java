package mg.gov.goodGovernment.citizen;

import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.report.Report;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Citizen {
    @Id
    private String id;
    private Long cin;
    private String firstName;
    private String lastName;
    // Date Of Birth
    private LocalDate dob;
    @Indexed(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "citizen")
    private Collection<Report> reports;

    public Citizen(Long cin, String firstName, String lastName, LocalDate dob, String email, String password) {
        this.cin = cin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }

    /**
     * Donner un identifiant unique à de cet object sous forme:<br>
     * ID = sha256(cin + firstname + lastname)
     */
    public void createId() {
        this.id = Hashing.sha256().hashString(
                this.cin + this.firstName + this.lastName,
                StandardCharsets.UTF_8
        ).toString();
    }
}