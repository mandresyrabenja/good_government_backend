package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.region.Region;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Report> findByRegion(Region region) {
        return reportRepository.findByRegion(region);
    }

    @Override
    public List<Report> findByRegionIsNull() {
        return reportRepository.findByRegionIsNull();
    }
}
