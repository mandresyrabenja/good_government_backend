package mg.gov.goodGovernment.report;

import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JpaRepository du table report
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByRegion(Region region);

    List<Report> findByRegionIsNull();

    List<Report> findByCitizen(Citizen citizen);

    @Query("SELECT new mg.gov.goodGovernment.report.MonthlyReportNumber( " +
                "EXTRACT(MONTH FROM DATE_TRUNC('month',r.date) ) AS  month, COUNT(r.id) AS reportNumber" +
            ")" +
            " " +
            "FROM Report r WHERE EXTRACT(YEAR FROM r.date) = ?1 " +
            "GROUP BY DATE_TRUNC('month',r.date)"
    )

    List<MonthlyReportNumber> getLastYearMonthlyReportNumber(Integer lastYear);
}