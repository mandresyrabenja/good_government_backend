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

    @org.springframework.data.annotation.Transient
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

    @org.springframework.data.annotation.Transient
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonBackReference("region_report")
    private Region region;

    @Column(nullable = false)
    private String status;

    public Report(Citizen citizen, LocalDate date, String title, String description, Double latitude, Double longitude, Region region, String status) {
        this.citizen = citizen;
        this.date = date;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.status = status;
    }
}
