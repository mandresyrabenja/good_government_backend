package mg.gov.goodGovernment.region;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.gov.goodGovernment.authentication.ApplicationUser;
import mg.gov.goodGovernment.security.AppUserRole;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Une implémentantion des services liées à l'entité Region. Cet classe implemente les services liées aux services
 * d'accès au base de donées et au rôle de sécurité de l'entité Region.
 *
 * @author Mandresy
 */
@Service
@AllArgsConstructor
@Slf4j
public class RegionServiceImpl implements RegionService, UserDetailsService {
    private final RegionRepository regionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Region region = findByName(name);
        return new ApplicationUser(
                region.getName(),
                passwordEncoder.encode( region.getPassword() ),
                AppUserRole.REGION.getGrantedAutorities(),
                true,
                true,
                true,
                true
        );
    }

    @Override
    public void createRegion(Region region) {
        // Le nom d'une région est unique
        if(regionRepository.existsByName(region.getName())) {
            throw new IllegalStateException("A region has already the same name as " + region.getName());
        }

        regionRepository.save(region);
    }

    @Override
    public Long count() {
        return this.regionRepository.count();
    }

    @Override
    public List<Region> findAllRegions(Integer page) {
        return regionRepository.findByOrderByName(PageRequest.of(page, 10));
    }

    @Override
    public void deleteRegion(Integer id) {
        Region region = regionRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Aucune region n'a cette ID")
        );
        regionRepository.delete(region);
    }

    @Transactional
    public void updateRegion(Integer id, String name, String password) {
        Region region = regionRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Aucune region n'a cette ID")
        );
        if( (name != null) && (name.length() > 0) && !Objects.equals(name, region.getName()) ) {
            region.setName(name);
        }
        if( (password != null) && (password.length() > 0) && !Objects.equals(password, region.getPassword()) ) {
            region.setPassword(password);
        }
    }

    @Override
    public Region findByIdRegion(Integer id) {
        return regionRepository.findById(id).orElse(null);
    }

    @Override
    public Long getPageNumber(Integer regionsPerPage) {
        Long regionsNumber = count();
        return ( ( regionsNumber-(regionsNumber%regionsPerPage) ) / regionsPerPage) + 1;
    }

    @Override
    public Region findByName(String name) {
        return regionRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new UsernameNotFoundException(String.format("No region has %s as name", name))
        );
    }

    @Override
    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }
}
