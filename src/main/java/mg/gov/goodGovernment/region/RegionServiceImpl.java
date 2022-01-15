package mg.gov.goodGovernment.region;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.gov.goodGovernment.security.AppUserRole;
import mg.gov.goodGovernment.authentication.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class RegionServiceImpl implements RegionService, UserDetailsService {
    private final RegionRepository regionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Region region = regionRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new UsernameNotFoundException(String.format("No region has %s as name", name))
        );
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

    public void createRegion(Region region) {
        // Le nom d'une région est unique
        if(regionRepository.existsByName(region.getName())) {
            throw new IllegalStateException("A region has already the same name as " + region.getName());
        }

        regionRepository.save(region);
    }

    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }

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

    public Region findByIdRegion(Integer id) {
        if(regionRepository.existsById(id)) {
            return regionRepository.findById(id).get();
        } else {
            return null;
        }
    }
}
