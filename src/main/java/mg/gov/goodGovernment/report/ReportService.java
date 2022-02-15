package mg.gov.goodGovernment.report;

import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.region.Region;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Interface du service d'accèss aux base de donnée de la classe Report
 * @author Mandresy
 */
public interface ReportService {

    /**
     * Avoir la liste des mot-clés des signalements de problèmes
     * @return la liste des mot-clés des signalements de problèmes
     */
    List<String> getKeywords();

    /**
     * Rechercher des signalements en utilisant un mot-clés
     * @param region region qui fait la recherche
     * @param keyword Mots-clés
     * @return Liste des signalements qui a le mots-clés
     */
    List<Report> searchReport(Region region, String keyword);

    /**
     * Avoir le top 5 des mots-clés les plus fréquents dans les signalements des problèmes
     * @return le top 5 des mots-clés les plus fréquents dans les signalements des problèmes
     */
    List<Object[]> top5MostRepetitiveKeyword();

    /**
     * Avoir les nombres des signalements mensuel de l'année dernière
     * @return les nombres des signalements mensuel de l'année dernière
     */
    List<MonthlyReportNumber> getLastYearMonthlyReportNumber();

    /**
     * Avoir la liste des signalements faits par un citoyen
     * @param citizen Le citoyen filtre
     * @return La liste des signalements faits par le citoyen
     */
    List<Report> findByCitizen(Citizen citizen, Integer page);

    /**
     * Inserer un signalement de problème aux base de données
     * @param report Report à inserer
     */
    void insert(Report report);

    /**
     * Mettre à jour un signalement de problème aux base de données
     * @param authentication Authentication de l'utilisateur connecté
     * @param id ID du report
     * @param regionId ID de la nouvelle région du report
     * @param status Status du report
     */
    void update(Authentication authentication, Long id, Integer regionId, String status);

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
    List<Report> findByRegion(Region region, Integer page);

    /**
     * Avoir la liste des signalèment pas encore affecté à une région
     * @return Liste des signalèment pas encore affecté à une région
     */
    List<Report> findByRegionIsNull(Integer page);

    /**
     * Avoir le top 6 des régions qui ont le plus des signalement
     */
    List<Object[]> top6RegionWithMostReport();
}