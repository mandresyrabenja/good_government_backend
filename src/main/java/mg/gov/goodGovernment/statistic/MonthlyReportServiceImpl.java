package mg.gov.goodGovernment.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyReportServiceImpl implements MonthlyReportService{
    private final MonthlyReportRepository monthlyReportRepository;

    @Override
    public void insertMonthlyReport(MonthlyReport monthlyReport) {
        this.monthlyReportRepository.save(monthlyReport);
    }

    @Override
    public List<MonthlyReport> fetchAllMonthlyReport() {
        return this.monthlyReportRepository.findAll();
    }

    @Override
    public List<MonthlyReport> getLastYearMonthlyReportNumber() {
        return this.monthlyReportRepository.getYearMonthlyReport(LocalDate.now().getYear() - 1);
    }
}
