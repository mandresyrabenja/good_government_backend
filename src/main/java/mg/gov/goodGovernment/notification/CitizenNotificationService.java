package mg.gov.goodGovernment.notification;

/**
 * Interface d'accès aux collection Notification de la base de données mongodb
 */
public interface CitizenNotificationService {

    /**
     * Trouver les notifications d'un citoyen
     * @param citizenEmail Email du citoyen
     * @return Les notifications du citoyen
     */
    CitizenNotification findByCitizenEmail(String citizenEmail);

    /**
     * Insérer un notification au base de données
     * @param notification Notification à insérer
     */
    void addNotification(CitizenNotification notification);

    /**
     * Mettre à jour un la liste des notifications d'un citoyen
     * @param citizenNotification La nouvelle liste des notification du citoyen
     */
    void update(CitizenNotification citizenNotification);
}
