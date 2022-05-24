package mg.gov.goodGovernment.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.report.Report;

/**
 * Modèle du notification que le citoyen reçois lorsqu'un de ses signalements de problème est terminé
 *
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
