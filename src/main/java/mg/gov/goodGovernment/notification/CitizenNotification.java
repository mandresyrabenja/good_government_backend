package mg.gov.goodGovernment.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.report.Report;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Vector;

/**
 * Modèle du collection mongodb contenant les notifications de chaques citoyens
 *
 * @author Mandresy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CitizenNotification {
    @Id
    private Long id;
    private Citizen citizen;
    private List<Notification> notifications;
    @Transient
    public static final String SEQUENCE_NAME = "report_sequence";

    public CitizenNotification(Citizen citizen) {
        this.citizen = citizen;
        this.notifications = new Vector<Notification>();
    }
}