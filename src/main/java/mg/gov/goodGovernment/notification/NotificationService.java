package mg.gov.goodGovernment.notification;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.database.SequenceGeneratorService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
    private final SequenceGeneratorService sequenceGenerator;
    private final NotificationRepository repository;

    public Notification addNotification(Notification notification) {
        notification.setId(
                sequenceGenerator.generateSequence(Notification.SEQUENCE_NAME)
        );
        return repository.save(notification);
    }
}
