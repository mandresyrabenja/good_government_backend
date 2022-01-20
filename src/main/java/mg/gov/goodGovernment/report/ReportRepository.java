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

    /**
     * Avoir le top 6 des régions qui ont le plus des signalement
     */
    @Query(
            value = "SELECT region.name as region, count(report.id) as nb_report " +
                    "FROM report JOIN region ON report.region_id = region.id " +
                    "GROUP BY region.name ORDER BY nb_report  DESC LIMIT 6",
            nativeQuery = true
    )
    List<Object[]> top6RegionWithMostReport();
}