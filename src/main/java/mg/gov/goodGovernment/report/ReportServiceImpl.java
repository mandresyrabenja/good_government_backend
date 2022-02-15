package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.notification.CitizenNotification;
import mg.gov.goodGovernment.notification.CitizenNotificationService;
import mg.gov.goodGovernment.notification.Notification;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public List<Report> searchReport(Region region, String keyword) {
        return this.reportRepository.search(region.getId(), keyword);
    }

    @Override
    public List<Object[]> top5MostRepetitiveKeyword() {
        return reportRepository.top5MostRepetitiveKeyword();
    }

    @Override
    public List<MonthlyReportNumber> getLastYearMonthlyReportNumber() {
        return reportRepository.getLastYearMonthlyReportNumber(LocalDate.now().getYear()-1);
    }

    @Override
    public List<Report> findByCitizen(Citizen citizen, Integer page) {
        return reportRepository.findByCitizen(citizen, PageRequest.of(page, 10));
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

                // Ajout de la nouvelle notification à la liste des notifications du citoyen
                String regionName = (String) authentication.getPrincipal();
                citizenNotifications.getNotifications().add(new Notification(dbReport, regionName, false));
                citizenNotificationService.update(citizenNotifications);
            }
        }

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
    public List<Report> findByRegion(Region region, Integer page) {
        return reportRepository.findByRegion(region, PageRequest.of(page, 10));
    }

    @Override
    public List<Report> findByRegionIsNull(Integer page) {
        return reportRepository.findByRegionIsNull(PageRequest.of(page, 10));
    }

    @Override
    public List<Object[]> top6RegionWithMostReport() {
        return reportRepository.top6RegionWithMostReport();
    }
}
