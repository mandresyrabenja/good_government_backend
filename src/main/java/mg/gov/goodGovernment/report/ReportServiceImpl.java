package mg.gov.goodGovernment.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation de l'interface du couche service de la classe Report
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;

    /**
     * Inserer un Report aux base de données
     * @param report Le Report à insérer
     */
    @Override
    public void insert(Report report) {
        reportRepository.save(report);
    }
}
