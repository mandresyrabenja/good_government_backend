package mg.gov.goodGovernment.report;

import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * JpaRepository du table report
 */
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * Avoir la liste des mots-clés dans les signalements des problèmes
     * @return la liste des mots-clés dans les signalements des problèmes
     */
    @Query(
            value = "SELECT keyword, count(lower(keyword)) as repetition\n" +
                    "FROM (\n" +
                    "         SELECT regexp_split_to_table(title, '\\s') as keyword\n" +
                    "         FROM report\n" +
                    "     ) AS k \n" +
                    "WHERE keyword != 'ny'\n" +
                    "GROUP BY keyword ORDER BY repetition DESC LIMIT 6",
            nativeQuery = true
    )
    List<Object[]> listKeywords();

    /**
     * Chercher les signalements qui contient les mots clés entrées
     * @param region_id ID du région qui fait la recherche
     * @param keyword Mots-clés
     * @return Liste des signalements qui contient les mots clés entrées
     */
    @Query("SELECT r FROM Report r WHERE r.region.id = :region_id AND " +
            "(lower(r.title) LIKE lower( concat('%', :keyword, '%') ) " +
            "OR lower(r.description) LIKE lower( concat('%', :keyword, '%') ) )")
    List<Report> search(@Param("region_id") Integer region_id, @Param("keyword") String keyword);

    List<Report> findByRegion(Region region, Pageable pageable);

    List<Report> findByRegionIsNull(Pageable pageable);

    List<Report> findByCitizen(Citizen citizen, Pageable pageable);

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

    /**
     * Avoir le top 5 des mots-clés les plus fréquents dans les signalements des problèmes
     * @return le top 5 des mots-clés les plus fréquents dans les signalements des problèmes
     */
    @Query(
            value = "SELECT keyword, count(*) as repetition\n" +
                    "FROM (\n" +
                    "         SELECT regexp_split_to_table(title, '\\s') as keyword\n" +
                    "         FROM report\n" +
                    "     ) AS k \n" +
                    "WHERE keyword != 'ny'\n" +
                    "GROUP BY keyword ORDER BY repetition DESC LIMIT 5",
            nativeQuery = true
    )
    List<Object[]> top5MostRepetitiveKeyword();
}