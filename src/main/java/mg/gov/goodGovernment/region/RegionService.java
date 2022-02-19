package mg.gov.goodGovernment.region;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service d'accès au base de données de l'entité Région
 * @author Mandresy
 */
public interface RegionService {
    List<Region> findAllRegions(Integer page);
    void createRegion(Region region);
    void updateRegion(Integer id, String name, String password);
    void deleteRegion(Integer id);
    Region findByIdRegion(Integer id);

    /**
     * Chercher une région à partir de son nom et un numéro de page.<br>
     * Une page contient 10 régions.
     * @param name Nom du région à chercher
     * @return La région correspondant aux critères
     */
    Region findByName(String name);
}
