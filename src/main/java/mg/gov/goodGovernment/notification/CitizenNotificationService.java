package mg.gov.goodGovernment.notification;

/**
 * Interface d'accès aux collection Notification de la base de données mongodb
 *
 * @author Mandresy
 */
public interface CitizenNotificationService {

    CitizenNotification findByCitizenEmail(String citizenEmail);

    void addNotification(CitizenNotification notification);

    void update(CitizenNotification citizenNotification);
}
