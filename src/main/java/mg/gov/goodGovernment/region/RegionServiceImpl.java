package mg.gov.goodGovernment.region;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository repository;

    public void createRegion(Region region) {
        Optional<Region> region1 = repository.findByName(region.getName());
        if (region1.isPresent()) {
            throw new IllegalStateException("Une région porte dejà le même nom");
        }
        repository.save(region);
    }

    public List<Region> findAllRegions() {
        return repository.findAll();
    }

    public void deleteRegion(Integer id) {
        Region region = repository.findById(id).orElseThrow(
                () -> new IllegalStateException("Aucune region n'a cette ID")
        );
        repository.delete(region);
    }

    @Transactional
    public void updateRegion(Integer id, String name, String password) {
        Region region = repository.findById(id).orElseThrow(
                () -> new IllegalStateException("Aucune region n'a cette ID")
        );
        if( (name != null) && (name.length() > 0) && !Objects.equals(name, region.getName()) ) {
            region.setName(name);
        }
        if( (password != null) && (password.length() > 0) && !Objects.equals(password, region.getPassword()) ) {
            region.setPassword(password);
        }
    }

    public Region findByIdRegion(Integer id) {
        if(repository.existsById(id)) {
            return repository.findById(id).get();
        } else {
            return null;
        }
    }
}
