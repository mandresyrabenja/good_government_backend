package mg.gov.goodGovernment.government;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mg.gov.goodGovernment.security.AppUserRole;
import mg.gov.goodGovernment.authentication.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GovernmentServiceImpl implements GovernmentService, UserDetailsService {
    private final GovernmentRepository governmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Government government = governmentRepository.findByLoginIgnoreCase(username).orElseThrow(
                () -> {
                    log.info(String.format("No gov account has %s as username", username));
                    return new UsernameNotFoundException(String.format("No gov account has %s as username", username));
                }
        );
        return new ApplicationUser(
                government.getLogin(),
                passwordEncoder.encode( government.getPassword() ),
                AppUserRole.GOVERNMENT.getGrantedAutorities(),
                true,
                true,
                true,
                true
        );
    }

    @Override
    public void createGovernment(Government government) {
        if( !governmentRepository.existsByLoginIgnoreCase(government.getLogin()) )
            governmentRepository.save(government);
        else
            throw new IllegalStateException("Username already exists");
    }

    @Override
    public List<Government> findAll() {
        return governmentRepository.findAll();
    }

}