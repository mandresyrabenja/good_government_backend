package mg.gov.goodGovernment.security.auth;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.security.AppUserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter( applicationUser -> applicationUser.getUsername().equals(username) )
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "admin",
                        passwordEncoder.encode("admin"),
                        AppUserRole.GOVERNMENT.getGrantedAutorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "mandresy",
                        passwordEncoder.encode("mandresy"),
                        AppUserRole.CITIZEN.getGrantedAutorities(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return applicationUsers;
    }
}
