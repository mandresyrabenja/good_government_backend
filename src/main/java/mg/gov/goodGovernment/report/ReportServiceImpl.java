package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Une implémentation de l'interface du couche service de la classe Report
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final RegionService regionService;

    @Override
    public List<Report> findByCitizen(Citizen citizen) {
        return reportRepository.findByCitizen(citizen);
    }

    @Override
    public void insert(Report report) { reportRepository.save(report); }

    @Override
    @Transactional
    public void update(Long id, Integer regionId, String status) {
        Report dbReport = reportRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Aucun signalement ne correspond à l'ID entré")
        );
        if(null != regionId) {
            dbReport.setRegion( regionService.findByIdRegion(regionId) );
        }
        if (null != status && !("".equals(status)) ) {
            if(!Status.isValidStatus(status))
                throw new IllegalStateException("Status invalide: Le status doit être 'new', 'processing' ou 'done'");

            dbReport.setStatus(status);
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
