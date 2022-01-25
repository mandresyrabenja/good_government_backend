package mg.gov.goodGovernment.statistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonthlyReportRepository extends JpaRepository<MonthlyReport, Long> {

    /**
     * Avoir le nombre des signalements par mois d'une année donnée
     * @param year année
     * @return le nombre des signalements par mois d'une année donnée
     */
    @Query("SELECT m "+
            "FROM MonthlyReport m " +
            "WHERE EXTRACT(YEAR FROM m.date) = ?1 " +
            "ORDER BY date ASC")
    List<MonthlyReport> getYearMonthlyReport(Integer year);

}
