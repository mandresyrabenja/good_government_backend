package mg.gov.goodGovernment.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.report.Report;

/**
 * Notification d'un problème terminé
 * @author Mandresy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Report solvedProblem;
    private String regionName;
    private Boolean isReadByCitizen;
}
