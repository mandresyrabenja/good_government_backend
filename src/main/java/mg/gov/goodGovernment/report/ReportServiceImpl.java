package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.notification.CitizenNotification;
import mg.gov.goodGovernment.notification.Notification;
import mg.gov.goodGovernment.notification.CitizenNotificationService;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Une implémentation de l'interface du couche service de la classe Report
 * @author Mandresy
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final RegionService regionService;
    private final CitizenNotificationService citizenNotificationService;

    @Override
    public List<Report> findByCitizen(Citizen citizen) {
        return reportRepository.findByCitizen(citizen);
    }

    @Override
    public void insert(Report report) { reportRepository.save(report); }

    @Override
    @Transactional
    public void update(Authentication authentication, Long id, Integer regionId, String status) {
        Report dbReport = reportRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Aucun signalement ne correspond à l'ID entré")
        );

        if( (null != status) && !("".equals(status)) ) {
            // Si l'utilisateur connecté est de type région
            // Alors il peut mettre à jour le status du signalement
            if( !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_REGION")) )
                throw new IllegalStateException("Seule une région peut mettre à jour le status d'un signalement");

            if(!Status.isValidStatus(status))
                throw new IllegalStateException("Status invalide: Le status doit être 'new', 'processing' ou 'done'");
            dbReport.setStatus(status);

            // Création d'une notification si le problème est marqué comme terminé
            if(Status.DONE.getStatus().equalsIgnoreCase( dbReport.getStatus() )) {
                CitizenNotification citizenNotifications = citizenNotificationService.findByCitizenEmail(
                        dbReport.getCitizen().getEmail()
                );
                dbReport.setRegion(dbReport.getRegion()); // Fetch.LAZY
//                // Récuperation des infos du région du signalement pour l'inclure dans le document mongodb
//                // parce que le mapping ORM n'est pas reconnu par mongodb
//                String regionName = (String) authentication.getPrincipal();
//                Region region =  regionService.findByName(regionName);
//                // Création d'une nouvelle instance de Report en incluant manuellement le région
//                Report solvedProblem = new Report(
//                        dbReport.getId(), dbReport.getCitizen(), dbReport.getDate(), dbReport.getTitle(),
//                        dbReport.getDescription(), dbReport.getLatitude(), dbReport.getLongitude(),
//                        region, dbReport.getStatus()
//                );
                citizenNotifications.getNotifications().add(new Notification(dbReport, false));
                citizenNotificationService.update(citizenNotifications);
            }
        };

        if(null != regionId) {
            // Si l'utilisateur connecté est de type gouvernement
            // Alors il peut affecté un signalement à une région
            if( !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GOVERNMENT")) )
                throw new IllegalStateException("Seul le gouvernement peut affecté un signalemet à une région");

            dbReport.setRegion( regionService.findByIdRegion(regionId) );
        }
    }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Aucun signalement correspondant")
        );
    }

    @Override
    public List<Report> findByRegion(Region region) {
        return reportRepository.findByRegion(region);
    }

    @Override
    public List<Report> findByRegionIsNull() {
        return reportRepository.findByRegionIsNull();
    }
}
