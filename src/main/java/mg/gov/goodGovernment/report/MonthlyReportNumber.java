package mg.gov.goodGovernment.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Nombre des signalements par mois
 *
 * @author Mandresy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyReportNumber {

    // Mois de 1 à 12
    private Integer month;
    private Long reportNb;

}
