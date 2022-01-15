package mg.gov.goodGovernment.report;

import org.springframework.stereotype.Service;

/**
 * Interface du service d'accèss aux base de donnée de la classe Report
 * @author Mandresy
 */
@Service
public interface ReportService {
    void insert(Report report);
}