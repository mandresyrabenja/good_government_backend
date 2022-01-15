package mg.gov.goodGovernment.report;

import mg.gov.goodGovernment.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository du table report
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByRegion(Region region);

    List<Report> findByRegionIsNull();
}