package mg.gov.goodGovernment.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.gov.goodGovernment.http.HttpResponse;
import mg.gov.goodGovernment.report.MonthlyReportNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/monthlyreports")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class MonthlyReportController {
    private final MonthlyReportService monthlyReportService;

    /**
     * Avoir le nombre des signalements chaque mois de l'année dernière
     * @return Liste des nombre des signalements par mois de l'année dernière
     */
    @GetMapping(path = "last-year")
    @PreAuthorize("hasRole('GOVERNMENT')")
    public List<MonthlyReport> lastYearMonthlyReportNumber() {
        return monthlyReportService.getLastYearMonthlyReportNumber();
    }

    @PostMapping
    @PreAuthorize("hasRole('GOVERNMENT')")
    public ResponseEntity<HttpResponse> insertMonthlyReport(@RequestBody MonthlyReport monthlyReport) {
        try {
            this.monthlyReportService.insertMonthlyReport(monthlyReport);
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.CREATED.value(), true, "Créer avec succès"
                    ),
                    HttpStatus.CREATED
            );
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(
                new HttpResponse(
                    HttpStatus.NOT_ACCEPTABLE.value(), true, e.getMessage()
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('GOVERNMENT')")
    public List<MonthlyReport> getAllMonthlyReports() {
        return monthlyReportService.fetchAllMonthlyReport();
    }

}
