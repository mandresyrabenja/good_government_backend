package mg.gov.goodGovernment.report;

import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Interface du service d'accèss aux base de donnée de la classe Report
 * @author Mandresy
 */
public interface ReportService {
    /**
     * Avoir la liste des signalements faits par un citoyen
     * @param citizen Le citoyen filtre
     * @return La liste des signalements faits par le citoyen
     */
    List<Report> findByCitizen(Citizen citizen);

    /**
     * Inserer un signalement de problème aux base de données
     * @param report Report à inserer
     */
    void insert(Report report);

    /**
     * Mettre à jour un signalement de problème aux base de données
     * @param id ID du report
     * @param regionId ID de la nouvelle région du report
     * @param status Status du report
     */
    void update(Long id, Integer regionId, String status);

    /**
     * find Report by ID
     * @param id ID  du Report
     * @return Report
     */
    Report findById(Long id);

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