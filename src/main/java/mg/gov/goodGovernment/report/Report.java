package mg.gov.goodGovernment.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

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
    @ManyToOne
    private Citizen citizen;
    private LocalDate date;
    private String title;
    private String description;
    private Long latitude;
    private Long longitude;
    @ManyToOne
    private Region region;
    private String status;
}
