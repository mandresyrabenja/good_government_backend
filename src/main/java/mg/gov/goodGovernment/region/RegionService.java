package mg.gov.goodGovernment.region;

import java.util.List;

public interface RegionService {
    public List<Region> findAllRegions();
    public void createRegion(Region region);
    public void updateRegion(Integer id, String name, String password);
    public void deleteRegion(Integer id);
    public Region findByIdRegion(Integer id);
}
