package mg.gov.goodGovernment.citizen;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.report.Report;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Citizen {
    @Id
    @SequenceGenerator(
            name = "sequence_citizen",
            sequenceName = "sequence_citizen"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_citizen"
    )
    private Long id;

    @Column(nullable = true)
    private Long cin;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // Date Of Birth
    @Column(nullable = false)
    @JsonProperty("dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @org.springframework.data.annotation.Transient
    @OneToMany(mappedBy = "citizen")
    @JsonManagedReference("citizen_report")
    private Collection<Report> reports;

    /**
     * Savoir si cet citoyen est majeur
     * @return <code>true</code> si vrai<br>
     *          <code>false</code> si faux
     */
    public Boolean isAdultCitizen() {
        return (getAge() >= 18) ? true : false;
    }

    /**
     * Avoir l'age de cet citoyen à partir de sa date de naissance
     * @return Age de cette citoyen
     */
    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public Citizen(Long cin, String firstName, String lastName, LocalDate dob, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.cin = cin;
        this.email = email;
        this.password = password;
    }
}