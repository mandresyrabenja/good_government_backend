package mg.gov.goodGovernment.security;

import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.government.Government;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import static mg.gov.goodGovernment.security.AppUserPermission.*;

/**
 * Configuration du securité de l'application
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                // CRUD region
                .antMatchers(HttpMethod.POST, "/api/v1/regions/**").hasAuthority(REGION_CREATE.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/v1/regions/**").hasAuthority(REGION_UPDATE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/v1/regions/**").hasAuthority(REGION_DELETE.getPermission())
                .antMatchers("/api/v1/regions/**").hasAuthority(REGION_READ.getPermission())

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails government = User.builder()
                .username("admin")
                .password( passwordEncoder.encode("admin") )
                .authorities(AppUserRole.GOVERNMENT.getGrantedAutorities())
                .build();

        UserDetails citizen = User.builder()
                .username("mandresy")
                .password( passwordEncoder.encode("1234") )
                .authorities(AppUserRole.CITIZEN.getGrantedAutorities())
                .build();

        return new InMemoryUserDetailsManager(
                government, citizen
        );
    }
}
