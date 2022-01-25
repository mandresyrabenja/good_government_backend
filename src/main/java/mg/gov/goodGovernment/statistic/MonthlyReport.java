package mg.gov.goodGovernment.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entité contenant le nombre des signalements fait ou mis à jour au cours d'un mois.
 * @author Mandresy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReport {
    @Id
    @SequenceGenerator(
            name = "sequence_monthlyreport",
            sequenceName = "sequence_monthlyreport"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_monthlyreport"
    )
    private Long id;
    private LocalDate date;
    // Nombre des nouveaux signalements fait pendant cet mois
    private Integer newReportNb;
    // Nombre des signalements en cours de traitement à la fin de cet mois
    private Integer processingReportNb;
    // Nombre des signalements marqué comme fini à la fin de cet mois
    private Integer doneReportNb;

    public MonthlyReport(LocalDate date, Integer newReportNb, Integer processingReportNb, Integer doneReportNb) {
        this.date = date;
        this.newReportNb = newReportNb;
        this.processingReportNb = processingReportNb;
        this.doneReportNb = doneReportNb;
    }
}
