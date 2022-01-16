package mg.gov.goodGovernment.security;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.citizen.CitizenServiceImpl;
import mg.gov.goodGovernment.government.GovernmentServiceImpl;
import mg.gov.goodGovernment.region.RegionServiceImpl;
import mg.gov.goodGovernment.security.jwt.JwtAuthentication;
import mg.gov.goodGovernment.security.jwt.JwtConfig;
import mg.gov.goodGovernment.security.jwt.JwtTokenVerifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

/**
 * Configuration du securité de l'application
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder passwordEncoder;
    private final RegionServiceImpl regionServiceImpl;
    private final GovernmentServiceImpl governmentServiceImpl;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final CitizenServiceImpl citizenServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // Cet application est utilisé par des clients web et mobile
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()

            .addFilter(new JwtAuthentication(authenticationManager(), jwtConfig, secretKey))
            .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtAuthentication.class)

            .authorizeRequests()
            // Tout le monde peut créer un compte citoyen
            .antMatchers(HttpMethod.POST, "/api/v1/citizens/**").permitAll()
            .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .authenticationProvider(regionAuthProvider())
            .authenticationProvider(govAuthProvider())
            .authenticationProvider(citizenAuthProvider());
    }

    /**
     * AuthenticationProvider d'un compte de région
     */
    public DaoAuthenticationProvider regionAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(regionServiceImpl);

        return provider;
    }

    /**
     * AuthenticationProvider d'un compte de gouvernement
     */
    public DaoAuthenticationProvider govAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(governmentServiceImpl);

        return provider;
    }

    /**
     * AuthenticationProvider d'un compte de citoyen
     */
    public DaoAuthenticationProvider citizenAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(citizenServiceImpl);

        return provider;
    }
}
