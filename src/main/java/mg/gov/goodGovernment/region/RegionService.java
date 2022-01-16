package mg.gov.goodGovernment.region;

import java.util.List;

public interface RegionService {
    List<Region> findAllRegions();
    void createRegion(Region region);
    void updateRegion(Integer id, String name, String password);
    void deleteRegion(Integer id);
    Region findByIdRegion(Integer id);

    /**
     * Chercher une région à partir de son nom
     * @param name Nom du région à chercher
     * @return La région correspondant
     */
    Region findByName(String name);
}
