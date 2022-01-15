package mg.gov.goodGovernment.report;

import mg.gov.goodGovernment.region.Region;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface du service d'accèss aux base de donnée de la classe Report
 * @author Mandresy
 */
@Service
public interface ReportService {
    /**
     * Inserer un signalement de problème aux base de données
     * @param report Le signalement
     */
    void insert(Report report);

    /**
     * Avoir la liste des signalèment fait dans une région
     * @param region La région filtre
     * @return Liste des signalèment fait dans une région
     */
    List<Report> findByRegion(Region region);

    /**
     * Avoir la liste des signalèment pas encore affecté à une région
     * @return Liste des signalèment pas encore affecté à une région
     */
    List<Report> findByRegionIsNull();

}