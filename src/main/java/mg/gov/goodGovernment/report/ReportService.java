package mg.gov.goodGovernment.report;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepository repository;
}
