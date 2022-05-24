package mg.gov.goodGovernment.notification;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.database.SequenceGeneratorService;
import org.springframework.stereotype.Service;

/**
 * Une implémentation de l'interface d'accès aux base de données
 *
 * @author Mandresy
 */
@Service
@AllArgsConstructor
public class CitizenNotificationServiceImpl implements CitizenNotificationService {
    private final SequenceGeneratorService sequenceGenerator;
    private final CitizenNotificationRepository citizenNotificationRepository;

    @Override
    public CitizenNotification findByCitizenEmail(String citizenEmail) {
        return citizenNotificationRepository.findByCitizenEmail(citizenEmail).orElseThrow(
                () -> new IllegalStateException("Aucune notification correspondant au citoyen entré")
        );
    }

    @Override
    public void addNotification(CitizenNotification notification) {
        notification.setId(
                sequenceGenerator.generateSequence(CitizenNotification.SEQUENCE_NAME)
        );
        citizenNotificationRepository.save(notification);
    }

    @Override
    public void update(CitizenNotification citizenNotification) {
        citizenNotificationRepository.save(citizenNotification);
    }
}
