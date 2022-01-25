package mg.gov.goodGovernment.statistic;

import java.util.List;

public interface MonthlyReportService {

    void insertMonthlyReport(MonthlyReport monthlyReport);
    List<MonthlyReport> fetchAllMonthlyReport();

    /**
     * Avoir le nombre des signalements par mois de l'année dernière
     * @return le nombre des signalements par mois de l'année dernière
     */
    List<MonthlyReport> getLastYearMonthlyReportNumber();
}
