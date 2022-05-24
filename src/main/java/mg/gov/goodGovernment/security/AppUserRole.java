package mg.gov.goodGovernment.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static mg.gov.goodGovernment.security.AppUserPermission.*;

/**
 * Les roles des utilisateurs pour spring security
 *
 * @author Mandresy
 */
@RequiredArgsConstructor
@Getter
public enum AppUserRole {
    GOVERNMENT(
        Sets.newHashSet(
            CITIZEN_READ, CITIZEN_DELETE
            ,REGION_CREATE, REGION_READ, REGION_UPDATE, REGION_DELETE
            , REPORT_READ, REPORT_UPDATE, REPORT_DELETE
            , NOTIFICATION_CREATE
        )
    )
    ,REGION(
        Sets.newHashSet(
            REGION_CREATE, REGION_READ, REPORT_UPDATE, REGION_DELETE
            ,REPORT_READ, REPORT_UPDATE, REPORT_DELETE
            ,NOTIFICATION_CREATE
            ,REGION_CREATE, REGION_READ, REGION_UPDATE, REGION_DELETE
        )
    )
    ,CITIZEN(
        Sets.newHashSet(
            CITIZEN_CREATE, CITIZEN_READ, CITIZEN_UPDATE, CITIZEN_DELETE
            ,REPORT_CREATE, REPORT_READ
            ,NOTIFICATION_READ, NOTIFICATION_UPDATE
        )
    );

    private final Set<AppUserPermission> permissions;

    /**
     * Avoir les authorities de cet role sous forme d'un Set
     * @return Set des authorities
     */
    public Set<SimpleGrantedAuthority> getGrantedAutorities() {

        // Affectation des authorities dans un Set
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(
                    permission -> new SimpleGrantedAuthority(permission.getPermission())
                )
                .collect(Collectors.toSet());

        // Ajout du role
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
