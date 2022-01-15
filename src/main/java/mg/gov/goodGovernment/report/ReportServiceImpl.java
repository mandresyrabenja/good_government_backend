package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.region.Region;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Une implémentation de l'interface du couche service de la classe Report
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;

    @Override
    public void insert(Report report) {
        reportRepository.save(report);
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
