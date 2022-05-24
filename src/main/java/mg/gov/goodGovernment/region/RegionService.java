package mg.gov.goodGovernment.region;

import java.util.List;

/**
 * Interface du service d'accès au base de données de l'entité Région
 *
 * @author Mandresy
 */
public interface RegionService {
    Long count();
    List<Region> findAllRegions(Integer page);
    void createRegion(Region region);
    void updateRegion(Integer id, String name, String password);
    void deleteRegion(Integer id);
    Region findByIdRegion(Integer id);

    /**
     * Avoir le nombre des pages s'une page contient regionsPerPage régions
     * @param regionsPerPage Nombre des régions par page
     * @return le nombre des pages s'une page contient regionsPerPage régions
     */
    Long getPageNumber(Integer regionsPerPage);

    /**
     * Chercher une région à partir de son nom et un numéro de page.<br>
     * Une page contient 10 régions.
     * @param name Nom du région à chercher
     * @return La région correspondant aux critères
     */
    Region findByName(String name);

    List<Region> findAllRegions();
}
