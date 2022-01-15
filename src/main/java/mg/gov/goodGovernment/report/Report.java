package mg.gov.goodGovernment.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Report {
    @Id
    @SequenceGenerator(
            name = "sequence_report",
            sequenceName = "sequence_report"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_report"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id")
    @JsonBackReference("citizen_report")
    private Citizen citizen;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne(optional = true)
    @JsonBackReference("region_report")
    private Region region;

    @Column(nullable = false)
    private String status;
}
